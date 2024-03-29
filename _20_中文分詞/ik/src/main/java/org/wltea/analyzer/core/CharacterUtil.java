/**
 * IK 中文分詞  版本 5.0
 * IK Analyzer release 5.0
 *
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * 原始程式碼由林良益(linliangyi2005@gmail.com)提供
 * 版權聲明 2012，烏龍茶工作室
 * provided by Linliangyi and copyright 2012 by Oolong studio
 *
 * 字元集識別工具類
 */
package org.wltea.analyzer.core;

/**
 *
 * 字元集識別工具類
 */
class CharacterUtil {

    public static final int CHAR_USELESS = 0;

    public static final int CHAR_ARABIC = 0X00000001;

    public static final int CHAR_ENGLISH = 0X00000002;

    public static final int CHAR_CHINESE = 0X00000004;

    public static final int CHAR_OTHER_CJK = 0X00000008;

    /**
     * 識別字元類型
     *
     * @param input
     * @return int CharacterUtil定義的字元類型常量
     */
    static int identifyCharType(char input) {
        if (input >= '0' && input <= '9') {
            return CHAR_ARABIC;

        } else if ((input >= 'a' && input <= 'z') || (input >= 'A' && input <= 'Z')) {
            return CHAR_ENGLISH;

        } else {
            Character.UnicodeBlock ub = Character.UnicodeBlock.of(input);

            if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
                    || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A) {
                // 目前已知的中文字元UTF-8集合
                return CHAR_CHINESE;

            } else if (ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS // 全形數位字元和日韓字元
                    // 韓文字元集
                    || ub == Character.UnicodeBlock.HANGUL_SYLLABLES
                    || ub == Character.UnicodeBlock.HANGUL_JAMO
                    || ub == Character.UnicodeBlock.HANGUL_COMPATIBILITY_JAMO
                    // 日文字元集
                    || ub == Character.UnicodeBlock.HIRAGANA // 平假名
                    || ub == Character.UnicodeBlock.KATAKANA // 片假名
                    || ub == Character.UnicodeBlock.KATAKANA_PHONETIC_EXTENSIONS) {
                return CHAR_OTHER_CJK;

            }
        }
        // 其他的不做處理的字元
        return CHAR_USELESS;
    }

    /**
     * 進行字元規格化（全形轉半形，大寫轉小寫處理）
     *
     * @param input
     * @return char
     */
    static char regularize(char input) {
        if (input == 12288) {
            input = (char) 32;

        } else if (input > 65280 && input < 65375) {
            input = (char) (input - 65248);

        } else if (input >= 'A' && input <= 'Z') {
            input += 32;
        }

        return input;
    }
}

