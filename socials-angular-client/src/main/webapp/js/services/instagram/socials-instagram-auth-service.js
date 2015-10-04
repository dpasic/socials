'use strict';

var socialsInstagramAuthService = angular.module('socialsInstagramAuthService', []);

socialsInstagramAuthService.factory('InstagramAuth', ['$http', 'REST_API',
    function ($http, REST_API) {
        return {
            getAuthUrlPromise: function () {
                return $http.get(REST_API.HOST + '/instagram/auth/url');
            }
        };
    }]);