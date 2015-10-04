'use strict';

var socialsUserHelperService = angular.module('socialsUserHelperService', []);

socialsUserHelperService.factory('UserHelper', ['localStorageService', function (localStorageService) {
        var selectedUserConstant = "selectedUser";

        return {
            setStorageSelected: function (user) {
                localStorageService.set(selectedUserConstant, user);
            },
            getStorageSelected: function (callback) {
                callback(localStorageService.get(selectedUserConstant));
            },
            clearStorageSelected: function () {
                localStorageService.remove(selectedUserConstant);
            }
        };
    }]);