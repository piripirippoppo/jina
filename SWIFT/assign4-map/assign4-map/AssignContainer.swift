//
//  Assign4MapContainer.swift
//  assign4-map
//
//  Created by jina kang on 4/25/20.
//  Copyright © 2020 jina kang. All rights reserved.
//

import Foundation
import UIKit

struct GPXPoint: Codable {
    var Latitude: Double
    var Longitude: Double
    var Altitude: Double
    var Time: Date
}

struct GPXSegment: Codable {
    var Coords : [GPXPoint]
}

struct GPXTrack : Codable {
    var Name : String
    var Link : String
    var Time : String
    var Segments : [GPXSegment] = []
    var Distance = "-"
    var FeetClimbed = "-"
}

struct MapContainer: Codable {
    var assigns: [GPXPoint] = []
    
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
