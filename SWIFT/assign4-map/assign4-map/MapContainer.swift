//
//  Assign4MapContainer.swift
//  assign4-map
//
//  Created by jina kang on 4/25/20.
//  Copyright © 2020 jina kang. All rights reserved.
//

import Foundation
import UIKit
import CoreData

struct GPXPoint: Codable {
    var latitude: Double
    var longitude: Double
    var time: Date
    var altitude: Double
}

struct GPXSegment: Codable {
    var coords : [GPXPoint]
}

struct GPXTrack : Codable {
    var name : String
    var link : String
    var time : String
    var segments : [GPXSegment] = []
    var distance = "-"
    var feetClimbed = "-"
}

class MapContainer: Codable {
    var assigns: [GPXTrack] = []
    
    init() {
        assigns = []
    }
    
    init?(json: Data) {
        if let decoded = try? JSONDecoder().decode(MapContainer.self, from: json) {
            assigns = decoded.assigns
        }
    }
    
    var json: Data? {
        return try? JSONEncoder().encode(self)
    }
}
