//
//  PrayerTimeViewModel.swift
//  iosApp
//
//  Created by Proxima Centauri on 27/01/21.
//  Copyright © 2021 orgName. All rights reserved.
//

import shared
import Foundation

class PrayerTimeViewModel: ObservableObject {
    
    let useCase = GetPrayerTimesUseCase()
    let locationUseCase = GetLocationUseCase()
    
    @Published var error: Error? = nil
    @Published var data: AzanEntity? = nil
    @Published var isLoading: Bool = false
    @Published var nextPray: String? = nil
    @Published var timeToNextPray: String? = nil
 
    func getData(completion: @escaping ((Int) -> Void)) {
        self.isLoading = true
        let timestamp: Int64 = Date().currentTimeMillis()
        var latitude: Double = -7.2756141
        var longitude: Double = -112.642642
        
        self.locationUseCase.execute(input: UseCaseNone()) { (data: LocationEntity?, error: Error?) -> Void in
            latitude = data?.latitude ?? -7.2756141
            longitude = data?.longitude ?? -112.642642

            self.useCase.execute(
                input: GetPrayerTimesUseCase.Input(timestamp: timestamp, latitude: latitude, longitude: longitude),
                completionHandler: { (data: AzanEntity?, error: Error?) -> Void in
                    self.isLoading = false
                    self.error = error
                    self.data = data
                    
                    completion(200)
            })
        }
    }
    
}
