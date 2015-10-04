'use strict';

var socialsUserAuthService = angular.module('socialsUserAuthService', []);

socialsUserAuthService.factory('UserAuth', ['$http', 'REST_API',
    function ($http, REST_API) {
        return {
            areCredentialsAuthenticatedPromise: function (credentials) {
                return $http.post(REST_API.HOST + '/users/' + credentials.username + '/authenticated', credentials.password);
            }
        };
    }]);