'use strict';

var socialsInstagramApiService = angular.module('socialsInstagramApiService', []);

socialsInstagramApiService.factory('InstagramApi', ['$http', 'REST_API',
    function ($http, REST_API) {
        return {
            getAccountInfoPromise: function (username) {
                return $http.get(REST_API.HOST + '/instagram/api/' + username + '/accountInfo');
            }
        };
    }]);