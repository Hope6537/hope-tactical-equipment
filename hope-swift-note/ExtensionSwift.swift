//
//  ExtensionSwift.swift
//  SwiftOOP
//
//  Created by Hope6537 on 15-1-27.
//  Copyright (c) 2015年 Hope6537. All rights reserved.
//

import Foundation

//拓展属性和方法
extension Double {
    static var name = "extension Double";
    static var factor : Double {
        return 12_500_000_000_000
    }
    var CHY : Double {
        return self / Double.factor;
    }
    var zeros : Int{
        var str = "\(self)";
        return str.lengthOfBytesUsingEncoding(NSUTF8StringEncoding) - 3;
    }
    func toString()->String{
        return Double.name;
    }
    static func getClassName()->String{
        return "\(name):\(self)"
    }
}
//拓展构造器
struct Sword{
    var length = 1.0;//有默认值
    var name = "test";
}
class Human{
    var sword : Sword;
    var name : String;
    init(name:String,sword:Sword){
        self.name = name;
        self.sword = sword;
    }
    convenience init(){
        self.init(name:"unimportant",sword:Sword());
    }
    func say(){
        println("name is \(name) , sword is \(sword.name)");
    }
}
extension Sword{
    init(newData:String){
        if(newData == "t1"){
            println("ahhhhhhhhhh!");
            self.init(length:3,name:"master");
        }
        else{
            //使用默认构造器，因为有属性具有默认值所以可用
            self.init();
        }
    }
}
extension Human{
    //class拓展只能够拓展便捷构造器
    convenience init(type:String){
        if(type == "woman"){
            println("my god!woman!oh yeah!come baby!big breast yo!");
            self.init(name : "cute girl" , sword:Sword(newData : "t1" ));
        }else{
            //调用便捷构造器
            self.init();
        }
    }
}
//拓展附属脚本
//通过下标还获取int的第几位值
extension Int{
    
    enum Result{
        case Found;
        case NotFound;
    }

    class Assistant{
        var result : Result = .Found;
        var name = "assistant1";
        func say()->String{
        
            switch result {
            case .Found:
                return "yes";
            case .NotFound:
                return "no";
            }
        }
    
    
    }
    static var assistant = Assistant();
    
    subscript (var index : Int) -> Int{
        var str = "\(self)";
        if(index >= str.utf16Count){
            Int.assistant.result = .NotFound;
            return -1;
        }
        else{
            var char = str[advance(str.startIndex,index)];
            var value = String(char).toInt();
            Int.assistant.result = .Found;
            return value!;
        }
    }
}

func testExtension(){
    var money : Double = 100_000_000_000_000;
    println("now \(money)");
    println("chy \(money.CHY)");
    println("now is \(money.toString())");
    println("has zero \(money.zeros)");
    println(Double.getClassName());
    
    var uncle = Human();
    uncle.say();
    var loli = Human(type:"woman");
    loli.say();
    
    println("\(1234567891011121345[2]) , isFound? \(Int.assistant.say())");
}