'use strict';

var socialsFeedService = angular.module('socialsFeedService', []);

socialsFeedService.factory('Feed', ['$http', 'REST_API',
    function ($http, REST_API) {
        return {
            getFeedPromise: function(username, socialNetworksList) {
                var networksString = '';
                for (var i = 0; i < socialNetworksList.length; i++) {
                    networksString += socialNetworksList[i];
                    if (i < socialNetworksList.length - 1) {
                        networksString += '+';
                    }
                }
                networksString = encodeURIComponent(networksString);
                return $http.get(REST_API.HOST + '/feed/' + username + '/socialNetworks?list=' + networksString);
            },
            postStatusesPromise: function(username, socialsPost) {
                return $http.post(REST_API.HOST + '/feed/' + username, socialsPost);
            }
        };
}]);