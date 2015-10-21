//
//  FieldOfSetGet.swift
//  SwiftOOP
//
//  Created by Hope6537 on 15-1-26.
//  Copyright (c) 2015年 Hope6537. All rights reserved.
//

import Foundation


//属性观察器之全局变量
var salary :Int = 200{
willSet(newValue){
    println("change the charge \(newValue)");
}
didSet(oldValue){
    if(salary >= (oldValue + 50)){
        println("amazing");
    }
}
}
func showResult(){
    salary = 250;
}


//下标方法
class Assistant{
    var interviewees:[Int:String] = [:];
    var name : String = "";
    //使用subscript关键字
    subscript (number : Int) -> String?{
        get{
            return interviewees[number];
        }
        set{
            interviewees[number] = newValue;
        }
    }
    subscript (number:Int , door : Int) ->String?{
        get{
            println("to the door of \(door)");
            return interviewees[number];
        }
    }
    //前面加上final即可防止被重写
    class func staticMethods(){
        println("yes");
    }
}

func testSetAndGetAndIndex(){

    
    showResult();
    println(People.staticParam);
    println(People.staticFunc());
    
    var myAssistant = Assistant();
    myAssistant.name = "assistant";
    //使用类似下标的格式直接进行赋值 而不需要操作数组，或者是字典
    myAssistant[2] = "name1";
    myAssistant[32] = "name2";
    //同样的，下标也可以多个值
    //注意使用!来解包
    println(myAssistant[2]!);
    println(myAssistant[32]!);
    println(myAssistant[2,31]!);
    
    Assistant_Son.staticMethods();

}