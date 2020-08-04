//
//  model.swift
//  assign2
//
//  Created by libraries on 3/8/20.
//  Copyright © 2020 Jin Ah Kang. All rights reserved.
//

import Foundation

enum Direction: String {
    case Up
    case Down
    case Left
    case Right
}

class Triples {
    var board: [[Int]]
    var score: Int
    var visitedUpdated: Bool
    init() {
        board = (1...4).map { _ in [0, 0, 0, 0] }
        score = 0
        visitedUpdated = false
    }
    /** reinitializes all model state. The value of rand
     ** should be derived from the Random/Deterministic segmented button. Note that this method
     ** should not call spawn() (calling newgame just initializes an empty game). Instead,
     ** the "action" for the newgame button should call
     ** newgame(), followed by four spawn()s. **/
    func newGame(rand: Bool){
        score = 0
        board = (1...4).map { _ in [0, 0, 0, 0] }
        
        if rand == true {
            srand48(Int.random(in: 0...1000))
        } else { //determ
            srand48(42)
            }
        }
    
    func prng(max: Int) -> Int {
        let ret = Int(floor(drand48() * (Double(max))))
        return (ret < max) ? ret : (ret-1)
    }
    
    /** collapse the model's board in the indicated
     ** direction, in place. Return true if movement occurred. You should
     ** define Direction to be an enum consisting of "Left", "Right", "Up", and
     ** "Down". Coincidentally, these are also the labels of the directional buttons. **/
    func collapse(dir: Direction) -> Bool {
        visitedUpdated = false
        
        switch dir {
        case .Up:
            self.rotate()
            self.rotate()
            self.rotate()
            self.shift()
            self.rotate()
             
        case .Down:
            self.rotate()
            self.shift()
            self.rotate()
            self.rotate()
            self.rotate()
            
        case .Right:
            self.rotate()
            self.rotate()
            self.shift()
            self.rotate()
            self.rotate()
        case .Left:
            self.shift()
        }
        return visitedUpdated
    }
    
    func shift(){ // collapse to the left
        var newBoard = board
        
        for row in 0..<board.count {
            for col in 0..<board[row].count - 1 {
                if(board[row][col] == 0){
                    newBoard[row][col] = board[row][col + 1]
                    newBoard[row][col + 1] = 0
                    board[row][col + 1] = 0
                    visitedUpdated = true
                } else {
                    if((board[row][col] == 1 && board[row][col + 1] == 2) ||
                        (board[row][col] == 2 && board[row][col + 1] == 1)){
                        newBoard[row][col] = board[row][col] + board[row][col + 1]
                        score += 3
                        newBoard[row][col + 1] = 0
                        board[row][col + 1] = 0
                        visitedUpdated = true
                    } else if ((board[row][col] >= 3 && board[row][col + 1] >= 3) && (board[row][col] == board[row][col + 1])) {
                        newBoard[row][col] = board[row][col] + board[row][col + 1]
                        score += newBoard[row][col]
                        newBoard[row][col + 1] = 0
                        board[row][col + 1] = 0
                        visitedUpdated = true
                    }
                }
            }
        }
        board = newBoard
    }
    
    public func rotate() {
        var newBoard = (1...4).map { _ in [0, 0, 0, 0] }
        
        for row in 0..<newBoard.count{
            for col in 0..<newBoard[row].count {
                newBoard[col][newBoard.count - 1 - row] = board[row][col]
            }
        }
        board = newBoard
    }
    
    /** which randomly chooses to create a new '1' or a '2', and puts it in an open
     ** tile, if there is one.  See Randomized Spawn below. **/
    
    /** First check to see if there's an open spot for a new tile. Do not call prng otherwise.
        Use prng to decide the value of the new tile.
        Use prng to decide where to place the new tile. **/
    func spawn(){
        let count = countEmpty()
        
        if count != 0 {
            var newC = -1
            let newVal = prng(max: 2) + 1
            let ind = prng(max: count)
            
            for row in 0..<board.count {
                for col in 0..<board[row].count {
                    if board[row][col] == 0 {
                        newC += 1
                        
                        if ind == newC {
                            board[row][col] = newVal
                            score += newVal
                            return;
                        }
                    }
                }
            }
        }
    } //end func
    
    func countEmpty() -> Int {
        var count = 0
        
        for row in 0..<board.count {
            for col in 0..<board[row].count {
                if board[row][col] == 0 {
                    count += 1
                }
            }
        }
        return count
    }
    
    /** returns true if there are no more possible moves. **/
    func isDone() -> Bool{
        let copyBoard = self.board
        let copyScore = self.score
        if self.countEmpty() > 0 || collapse(dir: .Up) || collapse(dir: .Down) || collapse(dir: .Right) || collapse(dir: .Left) {
            board = copyBoard
            score = copyScore
            return false
        }
        
        board = copyBoard
        score = copyScore
            return true
    }
}
