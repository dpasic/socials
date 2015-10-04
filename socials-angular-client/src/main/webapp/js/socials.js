'use strict';

var socials = angular.module('socials', [
    'ui.router', 'ngResource', 'ngLocalize', 'LocalStorageModule', 'angularModalService',
    'socialsApplication', 'socialsConstants',
    'socialsLogin', 'socialsRegister', 'socialsHome', 'socialsFeed',
    'socialsTwitterModalAccount', 'socialsFacebookModalAccount', 'socialsInstagramModalAccount', 'socialsPostingStatusModal',
    'socialsTwitterAuthService', 'socialsTwitterApiService', 'socialsFacebookAuthService', 'socialsFacebookApiService', 'socialsInstagramAuthService', 'socialsInstagramApiService',
    'socialsFacebookCredentialsService', 'socialsTwitterCredentialsService', 'socialsInstagramCredentialsService',
    'socialsUsersService', 'socialsUserHelperService', 'socialsUsersCredentialsService', 'socialsUserAuthService', 'socialsFeedService'
]);

socials
    .value('localeConf', {
        basePath: 'languages',
        defaultLocale: 'en-US',
        sharedDictionary: 'common',
        fileExtension: '.lang.json',
        persistSelection: true,
        cookieName: 'COOKIE_LOCALE_LANG',
        observableAttrs: new RegExp('^data-(?!ng-|i18n)'),
        delimiter: '::'
    })
    .value('localeSupported',
    ['en-US', 'hr-HR'].sort()).value(
    'localeFallbacks', {
        'en': 'en-US',
        'hr': 'hr-HR'
    });

socials.config(['$stateProvider', '$urlRouterProvider',
    function ($stateProvider, $urlRouterProvider) {
        $urlRouterProvider.otherwise('/login');
        $stateProvider
            .state('login', {
                url: '/login',
                templateUrl: 'partials/login.html',
                controller: 'LoginController'
            })
            .state('register', {
                url: '/register',
                templateUrl: 'partials/register.html',
                controller: 'RegisterController'
            })
            .state('home', {
                abstract: true,
                url: '/home',
                templateUrl: 'partials/home.html',
                controller: 'HomeController'
            })
            .state('home.feed', {
                url: '/feed',
                templateUrl: 'partials/feed.html',
                controller: 'FeedController'
            });
    }]);
