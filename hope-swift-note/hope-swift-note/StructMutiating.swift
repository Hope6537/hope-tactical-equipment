//
//  StructMutiating.swift
//  SwiftOOP
//
//  Created by Hope6537 on 15-1-26.
//  Copyright (c) 2015年 Hope6537. All rights reserved.
//

import Foundation

//要记住的是，在Swift中除了Class之外，其他的全都是值类型
//值类型有个方法叫做变质方法。用于修改当前的实例，而不是通过当前实例的基础上来修改属性

struct WarCraft {
    var sence : String;
    var money : Int;
    mutating func jumpToSence(sence:String){
        //在这里直接生产一个新的实例
        self = WarCraft(sence: "new Sence", money: 123);
    }
    func toString(){
        print("sence " + self.sence);
        print("money  \(self.money)");
    }
}

func testMutiating(){
    var myWarCraft = WarCraft(sence: "sence1", money: 1100);
    myWarCraft.toString();
    //after
    myWarCraft.jumpToSence("die");
    myWarCraft.toString();
}

