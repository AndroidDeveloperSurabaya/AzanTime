import SwiftUI
import CoreLocation.CLLocationManager

struct ContentView: View {
    
    @ObservedObject var viewRouter: ViewRouter
    
    var body: some View {
        GeometryReader { geometry in
            VStack {
                Spacer()
                switch viewRouter.currentPage {
                case .prayer:
                    PrayerListTab()
                case .setting:
                    SettingTab()
                }
                Spacer()
                ZStack {
                    HStack {
                        TabBarIcon(viewRouter: viewRouter, width: geometry.size.width/2, height: geometry.size.height/25,
                                   icon: "clock", page: .prayer)
                        Spacer()
                        TabBarIcon(viewRouter: viewRouter, width: geometry.size.width/2, height: geometry.size.height/25,
                                   icon: "gearshape", page: .setting)
                    }
                }
            }
        }
    }
}

struct TabBarIcon: View {
    
    @ObservedObject var viewRouter: ViewRouter
    @State var width: CGFloat = 100
    @State var height: CGFloat = 50
    @State var icon: String = ""
    @State var page: Router
    
    var body: some View {
        VStack {
            Rectangle()
                .foregroundColor(Color("ColorAccent"))
                .frame(height: 5)
            Image(systemName: icon)
                .resizable()
                .aspectRatio(contentMode: .fit)
                .frame(width: width, height: height)
                .padding(.top, 10)
                .foregroundColor(viewRouter.currentPage == page ? Color("ColorAccent") : .secondary)
        }
        .padding(.horizontal, -4)
        .onTapGesture {
            if (viewRouter.currentPage != page) {
                viewRouter.currentPage = page
            }
        }
    }
    
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView(viewRouter: ViewRouter())
    }
}
