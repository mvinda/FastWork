package com.example.fastwork.component.proguard;

/**
 * Created by Weihuada
 * 取决于作用位置:
 * 1、作用于Class/Interface，则类和接口的名称、方法都会保留（最高程度的保留，安全性较差）
 * 2、作用于方法，则方法名称保留，如果Class/Interface没有配置其他，
 * 只是配置到方法，则仅仅该方法保留，其他类名、方法名均被混淆
 */
public @interface KeepClass {
}
