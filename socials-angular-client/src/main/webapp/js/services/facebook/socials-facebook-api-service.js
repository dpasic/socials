'use strict';

var socialsFacebookApiService = angular.module('socialsFacebookApiService', []);

socialsFacebookApiService.factory('FacebookApi', ['$http', 'REST_API',
    function ($http, REST_API) {
        return {
            getAccountInfoPromise: function (username) {
                return $http.get(REST_API.HOST + '/facebook/api/' + username + '/accountInfo');
            }
        };
    }]);