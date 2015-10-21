//
//  ClassConstructor.swift
//  SwiftOOP
//
//  Created by Hope6537 on 15-1-26.
//  Copyright (c) 2015年 Hope6537. All rights reserved.
//

import Foundation

//构造类的实例
class Buddna {
    var name : String;
    //这是普通的指定构造器
    init(theFirstMethod name:String){
        print("first");
        self.name = name;
    }
    //构造器可以重载
    init(theSecondMethod name:String){
        print("second");
        self.name = name;
    }
    //便利构造器 调用其他构造器的方法
    convenience init (){
        print("convenience");
        self.init(theSecondMethod:"theFirst");
    }
    //可失败构造器，用于不符合条件时返回null对象
    init?(name : String){
        self.name = name;
        //返回失败必须在最后
        if(name == "failed"){
            return nil;
        }
    }
    deinit{
        print("destroy");
    }
}

//指定构造器中的链式调用父类构造器
class base1 {
    var name1 : String;
    init(){
        self.name1 = "test";
        print("base init \(name1)" );
    }
}
class base2 : base1{
    var name2 : String;
    override init(){
        self.name2 = "base2";
        print("base2 name \(name2)");
        //显示的调用superinit之后彩才可以使父类的值
        super.init();
        self.name1 = "name1";
    }
}


func testConstructor(){
    //调用构造方法的同时 我们对这个对象进行了创建 并具有一个强引用
    var buddna1:Buddna? = Buddna(theFirstMethod: "first");
    var buddna2 = Buddna(theSecondMethod: "second");
    //释放资源 调用destory方法
    //在1中可以直接赋值为nil 因为在定义的时候我们使用了可选类型
    buddna1 = nil;
    //否则就像2一样 只能由Buddna类型来装载
    buddna2 = Buddna(theFirstMethod: "123");
    
    /*
    output:
    base2 name base2
    base init test
    */
    _ = base2();
}