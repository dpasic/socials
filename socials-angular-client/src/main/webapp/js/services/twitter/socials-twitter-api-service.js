'use strict';

var socialsTwitterApiService = angular.module('socialsTwitterApiService', []);

socialsTwitterApiService.factory('TwitterApi', ['$http', 'REST_API',
    function ($http, REST_API) {
        return {
            getAccountInfoPromise: function (username) {
                return $http.get(REST_API.HOST + '/twitter/api/' + username + '/accountInfo');
            }
        };
    }]);