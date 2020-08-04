
//
//  MapControllerViewController.swift
//  assign4-map
//
//  Created by jina kang on 4/25/20.
//  Copyright © 2020 jina kang. All rights reserved.
//

import UIKit
import MapKit
import CoreData
import CoreLocation

class Mapcontroller: UIViewController, MKMapViewDelegate, CLLocationManagerDelegate {

    var document: MapDocument?
    var place: CLLocationCoordinate2D?
    var locationManager = CLLocationManager()
    var previousLocation: CLLocation?
    var allPoints: [CLLocationCoordinate2D] = []
    
    @IBOutlet weak var mapView: MKMapView!
    @IBOutlet weak var distancelabel: UILabel!
    @IBOutlet weak var speedLabel: UILabel!
    @IBOutlet weak var closeButton: UIButton!
    
    @IBAction func closeMapAction(_ sender: UIButton) {
        saveRide()
        locationManager.stopUpdatingLocation()
        
        dismiss(animated: true){
            self.document?.close(completionHandler: nil)
        }
    }
    
    @IBAction func mapTypeAction(_ sender: UISegmentedControl) {
        if sender.selectedSegmentIndex == 0 {
            mapView.mapType = .standard
        } else {
            mapView.mapType = .satellite
        }
    }
    
    func centerMap(loc: CLLocationCoordinate2D) {
        let radius: CLLocationDistance = 300
        let region = MKCoordinateRegion(
            center: loc,
            latitudinalMeters: radius,
            longitudinalMeters: radius)
        mapView.setRegion(region, animated: true)
    }
    
    private func startTracking() {
        locationManager = CLLocationManager()
        locationManager.delegate = self

        let status = CLLocationManager.authorizationStatus()
        
        if (status == .authorizedAlways || status == .authorizedWhenInUse) {
            updateLocation()
        } else {
            locationManager.requestWhenInUseAuthorization()
        }
    }
    
    private func updateLocation(){
        mapView.showsUserLocation = true
        locationManager.desiredAccuracy = kCLLocationAccuracyBest
        locationManager.startUpdatingLocation()
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()

        mapView.delegate = self
        
        if let loc = place {
            centerMap(loc: loc)
        }
        
        startTracking()
    
        //optional
        if let loc = place {
                   let circle = MKCircle(center: loc, radius: 10)
                   mapView.addOverlay(circle)
                   centerMap(loc: loc)
                   
                   let ann = MKPointAnnotation()
                   ann.title = "My Location"
                   ann.coordinate = loc
                   mapView.addAnnotation(ann)
               }
    }
    
     func mapView(_ mapView: MKMapView, rendererFor overlay: MKOverlay) -> MKOverlayRenderer {
         if let poly = overlay as? MKPolyline {
             let renderer = MKPolylineRenderer(overlay: poly)
             renderer.lineWidth = 3
             renderer.strokeColor = .red

             return renderer
         } else if let circle = overlay as? MKCircle {
             let renderer = MKCircleRenderer(circle: circle)
             renderer.lineWidth = 3
             renderer.strokeColor = .blue
             
             return renderer
         } else {
             return MKOverlayRenderer(overlay: overlay)
         }
     }
    
    func locationManager(_ manager: CLLocationManager, didFinishDeferredUpdatesWithError error: Error?) {
        print("Finished deferred: \(error!)")
    }
    
    func locationManager(_ manager: CLLocationManager, didFailWithError error: Error) {
        print("error: \(error)")
    }
    
    func locationManager(_ manager: CLLocationManager, didChangeAuthorization status: CLAuthorizationStatus) {
        if status == .authorizedAlways || status == .authorizedWhenInUse {
           updateLocation()
        }
    }
    
    var dist = 0.0
    
    func locationManager(_ manager: CLLocationManager, didUpdateLocations locations: [CLLocation]) {
        guard let latest = locations.first else { return }

        allPoints += locations.map { $0.coordinate }
        document?.updateChangeCount(.done)
        
        if previousLocation == nil {
            previousLocation = locations.first
        } else {
            let distanceInMeters = previousLocation?.distance(from: latest) ?? 0
            let distanceInMiles = distanceInMeters * 3.28 / 5280
            let duration = latest.timestamp.timeIntervalSince(previousLocation!.timestamp)
            let speed = distanceInMiles * (3600.0 / duration)
            
            dist += distanceInMiles
            distancelabel.text = String(format: " %.2f miles", dist)
            speedLabel.text = String(format: " %1.f mph", speed)
            
            let coords = [previousLocation!.coordinate] + locations.map { $0.coordinate }
                mapView.addOverlay(MKPolyline(coordinates: coords, count: coords.count))
            
            previousLocation = latest
            centerMap(loc: latest.coordinate)
            }
    }
    
    var rideName = 1
    
    func saveRide() {
        if let container = document?.container {
            let df = DateFormatter()
            df.dateFormat = "yyyy-MM-dd hh:mm:ss"
            let now = df.string(from: Date())
            
            container.assigns.append(GPXTrack(
                name: "Ride \(rideName)",
                link: "",
                time: now,
                segments: [GPXSegment(
                    coords: [GPXPoint(
                        latitude: place?.latitude ?? 0,
                        longitude: place?.longitude ?? 0,
                        time: Date(),
                        altitude: 0.00)])],
                distance: String(dist),
                feetClimbed: "-")
            )
            
            rideName += 1
        }
        
        allPoints = []
    }
        
    /*
    // MARK: - Navigation
    // In a storyboard-based application, you will often want to do a little preparation before navigation
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        // Get the new view controller using segue.destination.
        // Pass the selected object to the new view controller.
    }
    */

}
