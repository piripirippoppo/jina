//
//  ViewController.swift
//  assign2
//
//  Created by libraries on 3/8/20.
//  Copyright © 2020 Jin Ah Kang. All rights reserved.
//

import UIKit

class ViewController: UIViewController {

    //@IBOutlet var cv: UICollectionView!
    @IBOutlet var buttonOutlet: [UIButton]!
    @IBOutlet weak var upB: UIButton!
    @IBOutlet weak var downB: UIButton!
    @IBOutlet weak var rightB: UIButton!
    @IBOutlet weak var leftB: UIButton!
    @IBOutlet weak var newGameB: UIButton!
    @IBOutlet weak var segCon: UISegmentedControl!
    @IBOutlet weak var scoreLabel: UILabel!
    
    var triples = Triples()
    var NUM_ATTR = 3
    var row = 0
    var col = 0
    
    func updateGameView() {
        for btn in buttonOutlet {
            let colors = [UIColor.purple, UIColor.magenta, UIColor.blue]
            let num = triples.board[row][col]
            
            if num > 0 {
                btn.isHidden = false
                btn.setTitle(String(num), for:.normal)
                if num < 3 {
                btn.layer.backgroundColor = colors[num].cgColor
                } else {
                    btn.layer.backgroundColor = UIColor.white.cgColor
                }
            } else {
                btn.isHidden = true
            }
            if col < 3 {
                col += 1
            } else {
                row += 1
                col = 0
            }
        }
        row = 0
        col = 0
    }
        
     func showAlert() {
        if triples.isDone() {
            let alert = UIAlertController(title: "Game Over", message: " \(triples.score) points", preferredStyle: .alert)

            alert.addAction(UIAlertAction(title: "Bummer", style: .default, handler: nil))
            
            self.present(alert, animated: true)
        }
    }
    
    @IBAction func travel(_ sender: UIButton){
        switch sender {
        case upB:
            let val = triples.collapse(dir: .Up)
            triples.spawn()
            if val {
                updateGameView()
            }
                if triples.isDone() {
                    showAlert()
            }

            scoreLabel.text = String("Score: \(triples.score)")
        case downB:
            let val = triples.collapse(dir: .Down)
            triples.spawn()
            if val {
                updateGameView()
            } else {
                if triples.isDone() {
                    showAlert()
                }
            }
            scoreLabel.text = String("Score: \(triples.score)")
        case rightB:
            let val = triples.collapse(dir: .Right)
            triples.spawn()
            if val {
               updateGameView()
           } else {
               if triples.isDone() {
                   showAlert()
               }
                }
            scoreLabel.text = String("Score: \(triples.score)")
        default:
            let val = triples.collapse(dir: .Left)
            triples.spawn()
            if val {
                updateGameView()
            } else {
                if triples.isDone() {
                    showAlert()
                }
            scoreLabel.text = String("Score: \(triples.score)")
        }
    }
    }
    
    @IBAction func newGameStart(_ sender: UIButton){
        if(segCon.selectedSegmentIndex == 0){
            triples.newGame(rand: true)
            triples.spawn()
            triples.spawn()
            triples.spawn()
            triples.spawn()
            updateGameView()
            scoreLabel.text = String("Score: \(triples.score)")
        } else {
            triples.newGame(rand: false)
            triples.spawn()
            triples.spawn()
            triples.spawn()
            triples.spawn()
            updateGameView()
            scoreLabel.text = String("Score: \(triples.score)")
        }
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()
        // Do any additional setup after loading the view, typically from a nib.
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
}
