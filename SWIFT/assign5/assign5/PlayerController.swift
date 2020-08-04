//
//  PlayerController.swift
//  assign5
//
//  Created by jina kang on 5/2/20.
//  Copyright © 2020 jina kang. All rights reserved.
//

import UIKit
import Firebase
import AVKit

struct KeyValue : Comparable {
    static func < (lhs: KeyValue, rhs: KeyValue) -> Bool {
        return lhs.key < rhs.key
    }
    
    var key: String
    var val: String
}

struct Player {
    var like: Int
    var seen: Bool
    var desc: String
    var name: String
}

class PlayerController: UIViewController, UIGestureRecognizerDelegate {
    var rootRef: DatabaseReference!
    var nameList: [String] = []
    private var urlList: [String] = []
    var mMap: [KeyValue] = []
    var player: AVPlayer!
    var playerItem: AVPlayerItem!
    var playerLayer: AVPlayerLayer?
    var playerQue: AVQueuePlayer?
    var playerLooper: NSObject?
    var like = 0
    var desc: String?
    var seen: Bool = false
    var replaced = ""
    var single: UITapGestureRecognizer!
    var double: UITapGestureRecognizer!
    var p: [Player] = []
    
    @IBOutlet weak var likeButton: UIButton!
    @IBOutlet weak var likeLabel: UILabel!
    
    override func viewDidLoad() {
           super.viewDidLoad()
           rootRef = Database.database().reference()
           
           playVideo()
    }
    
    @IBAction func countLike(_ sender: Any) {
        like += 1
        likeLabel.text = "\(like)"
        
        let key = self.rootRef.child("urls/name").value(forKey: "name")
        print(key)
    }
    
    func hint() {
        let root = Database.database().reference()
        root.child("seen").setValue(nil)
        root.child("urls").setValue(nil)

        let vid1 = root.child("urls").childByAutoId()
        vid1.setValue(["name": "Neo romps happily in the snow",
                       "tags": "dog,awesome,pet",
                       "url": "https://sedna.cs.umd.edu/436clips/vids/neo.mp4"])
        root.child("seen/kendrick/\(vid1.key!)").setValue("1")
               
        root.child("urls").childByAutoId().setValue(["name": "Meditation Sheep", "tags": "sheep,peaceful",
                                                     "url": "https://sedna.cs.umd.edu/436clips/vids/sheep.mp4"])
        root.child("urls").childByAutoId().setValue(["name": "Beautiful Sunset", "tags": "sunset,peaceful",
                                                     "url": "https://sedna.cs.umd.edu/436clips/vids/sunset.mp4"])
        root.child("urls").childByAutoId().setValue(["name": "Horses", "tags": "horses,rural,peaceful",
                                                     "url": "https://sedna.cs.umd.edu/436clips/vids/horses.mp4",
                                                     "likes": ["someone": "1", "someone else": "1"]])
    }
    
    func playVideo() {
        rootRef.observe(.value, with: { (snapshot) in
            self.nameList = []
            self.urlList = []
            self.mMap = []
            self.replaced = ""
            self.p = []
            for child in snapshot.children {
                if let data = child as? DataSnapshot {
                    let dataVal = data.value as? NSDictionary
                    for (_, value) in dataVal! {
                        let line = "\(value)".components(separatedBy: ";")
                        self.nameList.append(line[0])
                        self.urlList.append(line[1])
                    }
                }
            }

            for i in 0..<self.urlList.count {
                self.nameList[i] = self.nameList[i].replacingOccurrences(of: "name = ", with: "")
                self.nameList[i] = self.nameList[i].replacingOccurrences(of: "\n", with: "")
                self.nameList[i] = self.nameList[i].replacingOccurrences(of: " ", with: "")
                self.nameList[i] = self.nameList[i].replacingOccurrences(of: "{", with: "")
                self.urlList[i] =  self.urlList[i].replacingOccurrences(of: "url = \"", with: "")
                self.urlList[i] = self.urlList[i].replacingOccurrences(of: "\"", with: "")
                self.urlList[i] = self.urlList[i].replacingOccurrences(of: " ", with: "")
                self.urlList[i] = self.urlList[i].replacingOccurrences(of: "\n", with: "")
                self.p.append(Player(like: self.like, seen: self.seen, desc: self.urlList[i], name: self.nameList[i]))
                self.mMap.append(KeyValue(key: self.nameList[i], val: self.urlList[i]))
                self.mMap.sort()
            }
            //self.description()
            
            let cue = URL(string: self.p[0].desc)!
            self.playerItem = AVPlayerItem(url: cue)
            self.playerQue = AVQueuePlayer(items: [self.playerItem])
            self.playerLayer = AVPlayerLayer(player: self.playerQue!)
            self.playerLooper = AVPlayerLooper(player: self.playerQue!, templateItem: self.playerItem)
            
            
            // description layer
            let url = (self.playerQue?.currentItem?.asset as? AVURLAsset)?.url

            let descLayer = CATextLayer()
            descLayer.foregroundColor = UIColor.blue.cgColor
            descLayer.string = String(describing: url)
            descLayer.font = UIFont(name: "Hevetica", size: 70)
            descLayer.shadowOpacity = 0.5
            descLayer.alignmentMode = CATextLayerAlignmentMode.left
            descLayer.frame = CGRect(x: 7, y: 340, width: 400, height: 20)
            
            //like button
            let btn = UIButton()
            //btn.backgroundColor = UIColor.white
            btn.setTitle("🦄 Like", for: .normal)
            btn.setTitleColor(UIColor.red, for: .normal)
            btn.frame = CGRect(x:300, y: 500, width: 100, height: 50)
            btn.addTarget(self, action: #selector(self.pressButton(button:)), for: .touchUpInside)
            btn.isEnabled = true
            btn.clipsToBounds = true
            btn.isUserInteractionEnabled = true
            
            self.view.layer.addSublayer(self.playerLayer!)
            self.view.layer.addSublayer(descLayer)
            self.view.layer.addSublayer(btn.layer)
            //self.view.sizeToFit()
            self.playerLayer?.frame = self.view.bounds
            self.playerQue?.play()
        })
    }
    @objc func pressButton(button: UIButton) {
        print("Worked")
    }
    
    @objc func singleTap(recog: UITapGestureRecognizer) {
        self.p[0].seen = true
        print("one")
        //self.playerLayer?.
    }
    @objc func doubleTap(recog: UITapGestureRecognizer) {
        print("two")
        self.playerQue?.advanceToNextItem()
    }
    
    func gestureRecognizer(_ gestureRecognizer: UIGestureRecognizer, shouldRequireFailureOf otherGestureRecognizer: UIGestureRecognizer) -> Bool {
          return (gestureRecognizer == single) && (otherGestureRecognizer == double)
      }

}
