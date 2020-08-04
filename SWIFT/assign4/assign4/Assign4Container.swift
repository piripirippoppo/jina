//
//  Assign4Container.swift
//  assign4
//
//  Created by jina kang on 4/24/20.
//  Copyright © 2020 jina kang. All rights reserved.
//

import Foundation
import UIKit

struct Assign: Codable {
    var center: CGPoint
    var radius: CGFloat
    
    init(c: CGPoint, r: CGFloat) {
        center = c
        radius = r
    }
}

class Assign4Container: Codable {
    var assigns: [Assign] = []
    
    init() {
        assigns = []
    }
    
    init?(json: Data) {
        if let decoded = try? JSONDecoder().decode(Assign4Container.self, from: json) {
            assigns = decoded.assigns
        }
    }
    
    var json: Data? {
        return try? JSONEncoder().encode(self)
    }
}
