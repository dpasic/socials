'use strict';

var socialsApplication = angular.module('socialsApplication', ['language-picker']);

var refreshingFeedPollTimer;

socialsApplication.controller('ApplicationController',
    ['$scope', '$interval', 'locale', 'localeSupported', 'localStorageService',
        function ($scope, $interval, locale, localeSupported, localStorageService) {

            $scope.isFirstTimeSignIn = false;

            function getCountry(locale) {
                var splitLocale = locale.split('-');
                if (splitLocale.length > 1) {
                    return splitLocale[1].toLowerCase();
                }
                return locale;
            }

            function createLanguageObj(locale) {
                var language = languageMappingList[locale] || {
                        nativeName: locale,
                        englishName: locale
                    };
                language.code = locale;
                language.country = language.country || getCountry(locale);
                return language;
            }

            $scope.supportedLangs = localeSupported;

            var localStorageName = 'language';
            var savedLanguage = localStorageService.get(localStorageName);
            var language = (savedLanguage || window.navigator.userLanguage || window.navigator.language).replace(
                /(\w+)[-_]?(\w*)/, function (match, p1, p2) {
                    if (p2) return p1.toLowerCase() + '-' + p2.toUpperCase();
                    return p1.toLowerCase();
                });
            locale.setLocale(language);
            $scope.currentlanguage = createLanguageObj(locale.getLocale());
            localStorageService.set(localStorageName, language);

            $scope.onLanguageChange = function (language) {
                locale.setLocale(language.code);
                $scope.currentlanguage = language;
                localStorageService.set(localStorageName, language.code);
            };

            $scope.$on("$stateChangeSuccess", function cancelRefreshingFeedTimer() {
                $interval.cancel(refreshingFeedPollTimer);
            });
        }]);