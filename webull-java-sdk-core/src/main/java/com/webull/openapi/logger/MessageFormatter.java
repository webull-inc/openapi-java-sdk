/*
 * Copyright 2022 Webull
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
/**
 * Copyright (c) 2004-2022 QOS.ch
 * All rights reserved.
 *
 * Permission is hereby granted, free  of charge, to any person obtaining
 * a  copy  of this  software  and  associated  documentation files  (the
 * "Software"), to  deal in  the Software without  restriction, including
 * without limitation  the rights to  use, copy, modify,  merge, publish,
 * distribute,  sublicense, and/or sell  copies of  the Software,  and to
 * permit persons to whom the Software  is furnished to do so, subject to
 * the following conditions:
 *
 * The  above  copyright  notice  and  this permission  notice  shall  be
 * included in all copies or substantial portions of the Software.
 *
 * THE  SOFTWARE IS  PROVIDED  "AS  IS", WITHOUT  WARRANTY  OF ANY  KIND,
 * EXPRESS OR  IMPLIED, INCLUDING  BUT NOT LIMITED  TO THE  WARRANTIES OF
 * MERCHANTABILITY,    FITNESS    FOR    A   PARTICULAR    PURPOSE    AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 * OF CONTRACT, TORT OR OTHERWISE,  ARISING FROM, OUT OF OR IN CONNECTION
 * WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 */
package com.webull.openapi.logger;

import com.webull.openapi.utils.StringUtils;

import java.util.HashSet;
import java.util.Set;

final class MessageFormatter {

    private MessageFormatter() {
    }

    private static final String DELIMITER = "{}";
    private static final char ESCAPE_CHAR = '\\';


    private static Throwable getThrowable(Object[] args) {
        int lastIndex = args.length - 1;
        Object last = args[lastIndex];
        if (last instanceof Throwable) {
            return (Throwable) last;
        } else {
            return null;
        }
    }

    public static FormattingTuple format(final String pattern, final Object... args) {
        if (args == null || args.length == 0) {
            return new FormattingTuple(pattern, null);
        }
        Throwable throwable = getThrowable(args);
        if (StringUtils.isBlank(pattern)) {
            return new FormattingTuple(pattern, throwable);
        }
        return format(pattern, args, throwable);
    }

    private static FormattingTuple format(final String pattern, final Object[] args, Throwable throwable) {
        int delimiterIdx = pattern.indexOf(DELIMITER);
        if (delimiterIdx == -1) {
            return new FormattingTuple(pattern, throwable);
        }

        StringBuilder builder = new StringBuilder(pattern.length() + 50);

        int charIdx = 0;
        int argIdx = 0;
        while (argIdx < args.length) {

            delimiterIdx = pattern.indexOf(DELIMITER, charIdx);
            if (delimiterIdx == -1) {
                // no more variables
                break;
            }

            if (isEscapedDelimiter(pattern, delimiterIdx)) {
                if (!isDoubleEscaped(pattern, delimiterIdx)) {
                    builder.append(pattern, charIdx, delimiterIdx);
                    charIdx = delimiterIdx + 1;
                } else {
                    builder.append(pattern, charIdx, delimiterIdx - 1);
                    deeplyAppendParameter(builder, args[argIdx], null);
                    charIdx = delimiterIdx + 2;
                    argIdx++;
                }
            } else {
                builder.append(pattern, charIdx, delimiterIdx);
                deeplyAppendParameter(builder, args[argIdx], null);
                charIdx = delimiterIdx + 2;
                argIdx++;
            }
        }
        builder.append(pattern, charIdx, pattern.length());
        return new FormattingTuple(builder.toString(), argIdx <= args.length - 1 ? throwable : null);
    }

    private static boolean isEscapedDelimiter(String pattern, int delimiterIndex) {
        return delimiterIndex > 0 && pattern.charAt(delimiterIndex - 1) == ESCAPE_CHAR;
    }

    private static boolean isDoubleEscaped(String pattern, int delimiterIndex) {
        return delimiterIndex >= 2 && pattern.charAt(delimiterIndex - 2) == ESCAPE_CHAR;
    }

    private static void deeplyAppendParameter(StringBuilder builder, Object arg, Set<Object[]> seenSet) {
        if (arg == null) {
            builder.append("null");
            return;
        }
        
        if (!arg.getClass().isArray()) {
            appendObject(builder, arg);
            return;
        }

        builder.append('[');
        if (arg instanceof boolean[]) {
            appendBooleanArray(builder, (boolean[]) arg);
        } else if (arg instanceof byte[]) {
            appendByteArray(builder, (byte[]) arg);
        } else if (arg instanceof char[]) {
            appendCharArray(builder, (char[]) arg);
        } else if (arg instanceof short[]) {
            appendShortArray(builder, (short[]) arg);
        } else if (arg instanceof int[]) {
            appendIntArray(builder, (int[]) arg);
        } else if (arg instanceof long[]) {
            appendLongArray(builder, (long[]) arg);
        } else if (arg instanceof float[]) {
            appendFloatArray(builder, (float[]) arg);
        } else if (arg instanceof double[]) {
            appendDoubleArray(builder, (double[]) arg);
        } else {
            appendObjectArray(builder, (Object[]) arg, seenSet);
        }
        builder.append(']');
    }

    private static void appendObject(StringBuilder builder, Object object) {
        try {
            String objectString = object.toString();
            builder.append(objectString);
        } catch (Throwable t) {
            System.err.println("Failed toString() invocation on an object of type [" + object.getClass().getName() + ']');
            t.printStackTrace();
            builder.append("[Failed toString()]");
        }

    }

    private static void appendObjectArray(StringBuilder builder, Object[] args, Set<Object[]> seenSet) {
        if (args.length == 0) {
            return;
        }
        if (seenSet == null) {
            seenSet = new HashSet<>(args.length);
        }
        if (!seenSet.contains(args)) {
            seenSet.add(args);
            final int len = args.length;
            for (int i = 0; i < len - 1; i++) {
                deeplyAppendParameter(builder, args[i], seenSet);
                builder.append(", ");
            }
            deeplyAppendParameter(builder, args[len - 1], seenSet);
            seenSet.remove(args);
        } else {
            builder.append("...");
        }
    }

    private static void appendBooleanArray(StringBuilder builder, boolean[] args) {
        final int len = args.length;
        for (int i = 0; i < len - 1; i++) {
            builder.append(args[i]);
            builder.append(", ");
        }
        builder.append(args[len - 1]);
    }

    private static void appendByteArray(StringBuilder builder, byte[] args) {
        final int len = args.length;
        for (int i = 0; i < len - 1; i++) {
            builder.append(args[i]);
            builder.append(", ");
        }
        builder.append(args[len - 1]);
    }

    private static void appendCharArray(StringBuilder builder, char[] args) {
        final int len = args.length;
        for (int i = 0; i < len - 1; i++) {
            builder.append(args[i]);
            builder.append(", ");
        }
        builder.append(args[len - 1]);
    }

    private static void appendShortArray(StringBuilder builder, short[] args) {
        final int len = args.length;
        for (int i = 0; i < len - 1; i++) {
            builder.append(args[i]);
            builder.append(", ");
        }
        builder.append(args[len - 1]);
    }

    private static void appendIntArray(StringBuilder builder, int[] args) {
        final int len = args.length;
        for (int i = 0; i < len - 1; i++) {
            builder.append(args[i]);
            builder.append(", ");
        }
        builder.append(args[len - 1]);
    }

    private static void appendLongArray(StringBuilder builder, long[] args) {
        final int len = args.length;
        for (int i = 0; i < len - 1; i++) {
            builder.append(args[i]);
            builder.append(", ");
        }
        builder.append(args[len - 1]);
    }

    private static void appendFloatArray(StringBuilder builder, float[] args) {
        final int len = args.length;
        for (int i = 0; i < len - 1; i++) {
            builder.append(args[i]);
            builder.append(", ");
        }
        builder.append(args[len - 1]);
    }

    private static void appendDoubleArray(StringBuilder builder, double[] args) {
        final int len = args.length;
        for (int i = 0; i < len - 1; i++) {
            builder.append(args[i]);
            builder.append(", ");
        }
        builder.append(args[len - 1]);
    }

}
