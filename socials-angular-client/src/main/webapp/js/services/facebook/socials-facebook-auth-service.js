'use strict';

var socialsFacebookAuthService = angular.module('socialsFacebookAuthService', []);

socialsFacebookAuthService.factory('FacebookAuth', ['$http', 'REST_API',
    function ($http, REST_API) {
        return {
            getAuthUrlPromise: function () {
                return $http.get(REST_API.HOST + '/facebook/auth/url');
            },
            areCredentialsVerifiedPromise: function (accessGrant) {
                return $http.post(REST_API.HOST + '/facebook/auth/verified', accessGrant);
            }
        };
    }]);