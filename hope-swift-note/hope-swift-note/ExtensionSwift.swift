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
        let str = "\(self)";
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
        print("name is \(name) , sword is \(sword.name)");
    }
}
extension Sword{
    init(newData:String){
        if(newData == "t1"){
            print("ahhhhhhhhhh!");
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
            print("my god!woman!oh yeah!come baby!big breast yo!");
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
        let str = "\(self)";
        if(index >= 64){
            Int.assistant.result = .NotFound;
            return -1;
        }
        else{
            let char = str.characters.count;
            let value = Int.init(char)
            Int.assistant.result = .Found;
            return value;
        }
    }
}

func testExtension(){
    let money : Double = 100_000_000_000_000;
    print("now \(money)");
    print("chy \(money.CHY)");
    print("now is \(money.toString())");
    print("has zero \(money.zeros)");
    print(Double.getClassName());
    
    let uncle = Human();
    uncle.say();
    let loli = Human(type:"woman");
    loli.say();
    
    print("\(1234567891011121345[2]) , isFound? \(Int.assistant.say())");
}