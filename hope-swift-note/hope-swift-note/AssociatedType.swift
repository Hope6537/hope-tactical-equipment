//
//  AssociatedType.swift
//  SwiftOOP
//
//  Created by Hope6537 on 15-4-8.
//  Copyright (c) 2015年 Hope6537. All rights reserved.
//

import Foundation

protocol Container{

    //关联类型名称
    typealias Itemtype;
    
    mutating func append(item : Itemtype);
    
    var count : Int{get}
    
    subscript(i:Int) -> Itemtype{get}
    
}

struct IntStack:Container{
    
    typealias Itemtype = Int;
    
    var items = [Int]();
    
    mutating func push(item:Int){
        
        items.append(item);
    
    }
    
    mutating func pop() -> Int{
        
        return items.removeLast();
    
    }
    
    mutating func append(item : Int){
        
        self.push(item)
        
    }
    
    var count : Int{
        
        return items.count;
    
    }
    
    subscript(i:Int) -> Int{
        
        return items[i];
        
    }
    
}

struct Stack<T>:Container{


    var items = [T]();
    
    mutating func push(item:T){
        items.append(item);
    }
    
    mutating func pop() -> T{
        return items.removeLast();
    }
    
    mutating func append(item : T){
        self.push(item);
    }
    
    var count:Int{
        return items.count;
    }
    
    subscript(i:Int)-> T{
    
        return items[i];
    
    }
}


func allItemsMatch<C1:Container,C2:Container where C1.Itemtype == C2.Itemtype , C1.Itemtype:Equatable>(SomeContainer:C1,anotherConatiner:C2)->Bool{
    if SomeContainer.count != anotherConatiner.count{
        return false;
    }
    for i in 0..<SomeContainer.count {
        if SomeContainer[i] != anotherConatiner[i]{
            return false;
        }
    }
    return true;
}

extension Array:Container{}

func testAssoicatedType(){

    var stackOfString = Stack<String>();
    
    stackOfString.push("1233");
    stackOfString.push("12312");
    stackOfString.push("412");
    
    var arrayOfStrings = ["1233","12312","412"];
    
    if allItemsMatch(stackOfString, arrayOfStrings){
        println("all match");
    }else{
        println("not all match");
    }
}