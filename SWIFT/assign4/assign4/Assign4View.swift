//
//  Assign4View.swift
//  assign4
//
//  Created by jina kang on 4/24/20.
//  Copyright © 2020 jina kang. All rights reserved.
//

import UIKit

class Assign4View: UIView {

    var document: Assign4Document?

    override func draw(_ rect: CGRect) {
        if let doc = document, let container = doc.container {
            let path = UIBezierPath()
            for assign in container.assigns {
                path.append(UIBezierPath(
                    arcCenter: assign.center,
                    radius: assign.radius,
                    startAngle: 0,
                    endAngle: .pi * 2,
                    clockwise: true))
            }
            UIColor.green.setStroke()
            path.lineWidth = 3
            path.stroke()
        }
    }
    /*
    // Only override draw() if you perform custom drawing.
    // An empty implementation adversely affects performance during animation.
    override func draw(_ rect: CGRect) {
        // Drawing code
    }
    */

}
