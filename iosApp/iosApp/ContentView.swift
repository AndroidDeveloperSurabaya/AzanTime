import SwiftUI
import shared
import Foundation

func greet() -> String {
    return "'OK'"
}

extension NSDate {
    func currentTimeMillis() -> Int64 {
        return Int64(self.timeIntervalSince1970 * 1000)
    }
}

class PrayerTimeViewModel: ObservableObject {
    
    let useCase = GetPrayerTimesUseCase()
    let locationUseCase = GetLocationUseCase()
    
    @Published var error: Error? = nil
    @Published var data: AzanEntity? = nil
    @Published var isLoading: Bool = false
 
    func getData() {
        debugPrint("Get Data")
        self.isLoading = true
        let timestamp: Int64 = NSDate().currentTimeMillis()
        let latitude: Double = -7.2756141
        let longitude: Double = -112.642642
        useCase.execute(
            input: GetPrayerTimesUseCase.Input(timestamp: timestamp, latitude: latitude, longitude: longitude),
            completionHandler: { (data: AzanEntity?, error: Error?) -> Void in
                debugPrint(error)
                debugPrint(data)
                self.isLoading = false
                self.error = error
                self.data = data
        })
        locationUseCase.execute(
            input: UseCaseNone(),
            completionHandler: { (data: LocationEntity?, error: Error?) -> Void in
                debugPrint("Get Location")
                debugPrint(error)
                debugPrint(data)
        })
    }
    
}

struct ContentView: View {
    
    @ObservedObject var viewModel: PrayerTimeViewModel = PrayerTimeViewModel()
    
    var body: some View {
        Group {
            if (viewModel.isLoading) {
                Text("Loading")
            } else {
                if (viewModel.error != nil) {
                    Text(viewModel.error.debugDescription)
                } else if (viewModel.data != nil){
                    Text(String(format: "Status: %d", viewModel.data!.code))
                } else {
                    Text("Unknown")
                }
            }
        }.onAppear {
            self.viewModel.getData()
        }
    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
    }
}
