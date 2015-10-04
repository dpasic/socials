'use strict';

var socialsTwitterCredentialsService = angular.module('socialsTwitterCredentialsService', []);

socialsTwitterCredentialsService.factory('TwitterCredentials', ['$resource', 'REST_API',
    function ($resource, REST_API) {
        return $resource(REST_API.TWITTER_CREDENTIALS, {});
    }]);