'use strict';

var socialsFacebookCredentialsService = angular.module('socialsFacebookCredentialsService', []);

socialsFacebookCredentialsService.factory('FacebookCredentials', ['$resource', 'REST_API',
    function ($resource, REST_API) {
        return $resource(REST_API.FACEBOOK_CREDENTIALS, {});
    }]);