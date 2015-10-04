'use strict';

var socialsTwitterAuthService = angular.module('socialsTwitterAuthService', []);

socialsTwitterAuthService.factory('TwitterAuth', ['$http', 'REST_API',
    function ($http, REST_API) {
        return {
            getRequestTokenPromise: function () {
                return $http.get(REST_API.HOST + '/twitter/auth/requestToken');
            }
        };
    }]);