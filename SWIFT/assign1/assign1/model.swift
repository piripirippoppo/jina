//
//  model.swift
//  assign1
//
//  Created by libraries on 2/12/20.
//  Copyright Â© 2020 Jin Ah Kang. All rights reserved.
//

import Foundation

enum Direction: String {
    case up
    case down
    case left
    case right
}

class Triples {
    var board: [[Int]]

    init() {
        board = (1...4).map { _ in [0, 0, 0, 0] }
    }
    
    func newgame() {// re-inits 'board', and any other state you deifine
        board = (1...4).map { _ in [0, 0, 0, 0] } // can I init again?
    }
    
    func rotate() { // rotate a square 2D Int array clockwise
        board = rotate2DInts(input: board)
    }
    
    func shift() { // collapse to the left
//        var newBoard = (1...4).map { _ in [0, 0, 0, 0]}
        var newBoard = board
       
        for row in 0..<board.count {
            for col in 0..<board[row].count - 1 {
                if(board[row][col] == 0){
                    newBoard[row][col] = board[row][col + 1]
                    newBoard[row][col + 1] = 0
                    board[row][col + 1] = 0
                } else {
                    if((board[row][col] == 1 && board[row][col + 1] == 2) ||
                        (board[row][col] == 2 && board[row][col + 1] == 1)){
                        newBoard[row][col] = board[row][col] + board[row][col + 1]
                        newBoard[row][col + 1] = 0
                        board[row][col + 1] = 0
                    } else if ((board[row][col] >= 3 && board[row][col + 1] >= 3) && (board[row][col] == board[row][col + 1])) {
                        newBoard[row][col] = board[row][col] + board[row][col + 1]
                        newBoard[row][col + 1] = 0
                        board[row][col + 1] = 0
                    }
                }
            }
        }
        board = newBoard
    }
    
    // collapse in specified direction using shift() and rotate()
    func collapse(dir: Direction) { //Direction is enum
        switch dir {
        case .up: self.rotate()
                  self.rotate()
                  self.rotate()
                  self.shift()
                  self.rotate()
                  
            
        case .down: self.rotate()
                    self.shift()
                    self.rotate()
                    self.rotate()
                    self.rotate()
            
        case .left: self.shift()
            
        case .right: self.rotate()
                     self.rotate()
                     self.shift()
                     self.rotate()
                     self.rotate()
        }
    }
}

    //  class-less function that will return of any square 2D Int array rotated clockwise
public func rotate2DInts(input: [[Int]]) -> [[Int]] {
    var newBoard = (1...4).map { _ in [0, 0, 0, 0] }

        for row in 0..<input.count{
            for col in 0..<input[row].count {
                newBoard[col][input.count - 1 - row] = input[row][col]
            }
        }
        return newBoard
    }


    // or generic type (if not possible, dont do it)
public func rotate2D<T>(input: [[T]]) -> [[T]] {
    var newBoard: [[T]] = []
    
    for row in 0..<input.count {
        var ele: [T] = []
        for col in stride(from: 3, through: 0, by: -1){
            ele.append(input[col][row])
        }
        newBoard.append(ele)
    }
       return newBoard
    }
