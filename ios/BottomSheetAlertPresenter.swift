//
//  BottomSheetAlertPresenter.swift
//  testBottom
//
//  Created by Sergei Golishnikov on 13/03/2021.
//

import Foundation
import UIKit

weak var previousBottomSheet: UIAlertController?

class AppAlertController: UIAlertController {
    var onDismiss: (() -> Void)?
    deinit {
        onDismiss?()
        onDismiss = nil
        debugPrint("deinit AppAlertController")
    }
}

class BottomSheetAlertPresenter {
    var strongSelf: BottomSheetAlertPresenter?
    
    var alertWindow: UIWindow?
    var currentAlert: UIAlertController?
    
    private func createAlertWindow() {
        if alertWindow != nil { return }
        let frame = RCTSharedApplication()?.keyWindow?.bounds ?? UIScreen.main.bounds
        alertWindow = UIWindow(frame: frame)
        alertWindow?.rootViewController = UIViewController()
        alertWindow?.windowLevel = UIWindow.Level.alert + 1
        alertWindow?.makeKeyAndVisible()
    }
    
    func present(options: NSDictionary, callback: @escaping RCTResponseSenderBlock) {
        strongSelf = self
        createAlertWindow()
        
        var isDark = false
        if #available(iOS 12.0, *) {
            isDark = UIScreen.main.traitCollection.userInterfaceStyle == .dark
        }
        if let theme = options["theme"] as? String {
            isDark = theme == "dark"
        }
        
        let completion = { [weak self] in
            guard let self = self else { return }
            previousBottomSheet = nil
            
            let alert = AppAlertController(
                title: options["title"] as? String,
                message: options["message"] as? String,
                preferredStyle: .actionSheet
            )
            
            alert.view.tintColor = RCTConvert.uiColor(options["iosTintColor"])

            let buttons = options["buttons"] as? Array<NSDictionary> ?? []
            if buttons.isEmpty { return }

            for (i, button) in buttons.enumerated() {
                
                var style: UIAlertAction.Style = .default
                if button["style"] as? String == "cancel" {
                    style = .cancel
                }
                
                if button["style"] as? String == "destructive" {
                    style = .destructive
                }
                
                alert.addAction(.init(title: button["text"] as? String, style: style, handler: { _ in
                    debugPrint("cancel")
                    callback([i])
                }))
                previousBottomSheet = alert
            }
            guard let controller = self.alertWindow?.rootViewController else { return }
            if #available(iOS 13.0, *) {
                alert.overrideUserInterfaceStyle = isDark ? .dark : .light
            }
            controller.present(alert, animated: true)
            alert.onDismiss = { [weak self] in
                debugPrint("didDismiss")
                controller.removeFromParent()
                previousBottomSheet = nil
                self?.alertWindow = nil
                self?.strongSelf = nil
            }
        }
        
        if previousBottomSheet == nil { completion() }
        else { previousBottomSheet?.dismiss(animated: true, completion: completion) }
    }
    
    deinit {
        debugPrint("deinit presenter")
    }
}
