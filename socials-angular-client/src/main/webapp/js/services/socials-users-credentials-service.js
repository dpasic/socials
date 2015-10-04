'use strict';

var socialsUsersCredentialsService = angular.module('socialsUsersCredentialsService', []);

socialsUsersCredentialsService.factory('UsersCredentials', ['$resource', 'REST_API',
    function ($resource, REST_API) {
        return $resource(REST_API.USERS_CREDENTIALS, {});
    }]);