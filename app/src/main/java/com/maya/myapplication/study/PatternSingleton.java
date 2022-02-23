//package com.maya.myapplication.study;
//
//public class PatternSingleton {
//    private static PatternSingleton singleton;
//    int a = 0;
//    private PatternSingleton(int a){
//    this.a=a;
//    }
//    public static  PatternSingleton start( int a){
//        if (singleton==null){
//            singleton=new PatternSingleton(a);
//        }
//        return singleton;
//    }
//}
//
//class str{
//    public static void main(String[] args) {
//        PatternSingleton patternSingleton1 =  PatternSingleton.start(1);
//        PatternSingleton patternSingleton2 =  PatternSingleton.start(2);
//        System.out.println(patternSingleton1==patternSingleton2);
//    }
//}
//