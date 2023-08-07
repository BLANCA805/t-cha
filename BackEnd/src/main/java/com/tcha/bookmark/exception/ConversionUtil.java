//package com.tcha.bookmark.exception;
//
//
//import java.util.UUID;
//
//public class ConversionUtil {
//
//    public static Long stringToLong(String target, Long defaultValue) {
//        try {
//            return Long.valueOf(target);
//        } catch (NumberFormatException e) { //에러나면 여기서 businesslogicExceptiong해도 될듯 <= 더 세분화
//            return defaultValue;
//        }
//    }
//
//    public static UUID stringToUUID(String target, UUID defaultValue) {
//        try {
//            return UUID.fromString(target);
//        } catch (IllegalArgumentException e) {
//            return defaultValue;
//        }
//    }
//
//}
