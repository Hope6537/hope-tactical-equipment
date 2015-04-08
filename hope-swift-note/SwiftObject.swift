//
//  SwiftObject.swift
//  SwiftOOP
//
//  Created by Hope6537 on 15-1-26.
//  Copyright (c) 2015年 Hope6537. All rights reserved.
//

import Foundation

class People{
    var name : String = "";
    var apperance : String = "";
    var dream : String = "";
    var reality : String = "";
    //属性观察器模式
    var lover : People?{
        //属性改变前触发
        willSet(newLover){
            if(self.lover != nil){
                if(newLover != nil){
                    println("\(self.name) lover has changed to \(newLover)");
                }
            }
            else{
                if(newLover != nil){
                    println("\(self.name) has found first love  \(newLover)");
                }
            }
        }
        //属性改变后触发
        didSet(oldLover){
            if(oldLover != nil){
                println("\(self.name) has left the oldlover \(oldLover)");
            }
        }
    };
    //计算属性
    var isHappy : Bool{
        get{
            if(dream != "" && dream == reality || lover != nil || apperance == "handsome"){
                return true;
            }else{
                return false;
            }
        }
        set{
            if(newValue == true){
                apperance = "handsome";
            }else{
                apperance = "pool";
            }
        }
    }
    //这个是类型属性 被所有的People对象所共有
    class var plannet : String{
        //同样可以写set和get
        get{
        return "地球";
        }
        set{
            
        }
    }
    class var staticParam : String{
        return "静态变量";
    }
    class func staticFunc(){
        println("静态方法");
        println(staticParam);
    }
}

func testObject(){
    var oldCoder = People();
    println("Old Coder is Happy :\(oldCoder.isHappy)");
    oldCoder.isHappy = true;
    println("app : \(oldCoder.apperance)");
    println(People.plannet);
}


