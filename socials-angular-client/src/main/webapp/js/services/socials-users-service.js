'use strict';

var socialsUsersService = angular.module('socialsUsersService', []);

socialsUsersService.factory('Users', ['$resource', 'REST_API',
    function ($resource, REST_API) {
        return $resource(REST_API.USERS, {});
    }]);
