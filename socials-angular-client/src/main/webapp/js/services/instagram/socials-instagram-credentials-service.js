'use strict';

var socialsInstagramCredentialsService = angular.module('socialsInstagramCredentialsService', []);

socialsInstagramCredentialsService.factory('InstagramCredentials', ['$resource', 'REST_API',
    function ($resource, REST_API) {
        return $resource(REST_API.INSTAGRAM_CREDENTIALS, {});
    }]);