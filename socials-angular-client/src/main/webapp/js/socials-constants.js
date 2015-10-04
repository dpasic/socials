'use strict';

var socialsConstants = angular.module('socialsConstants', []);

socialsConstants.config(['$resourceProvider', function ($resourceProvider) {
    $resourceProvider.defaults.stripTrailingSlashes = false;
}]);

socialsConstants
    .constant(
    'REST_API',
    {
        HOST: 'http://localhost:8080/socials-rest',
        USERS: 'http://localhost:8080/socials-rest/users/:username',
        USERS_CREDENTIALS: 'http://localhost:8080/socials-rest/users/:username/credentials',
        FACEBOOK_CREDENTIALS: 'http://localhost:8080/socials-rest/facebook/auth/:username/credentials',
        TWITTER_CREDENTIALS: 'http://localhost:8080/socials-rest/twitter/auth/:username/credentials',
        INSTAGRAM_CREDENTIALS: 'http://localhost:8080/socials-rest/instagram/auth/:username/credentials'
    })
    .constant(
    'URI',
    {
        OPEN_AUTH: 'http://localhost:8090/socials/oauth/prepare',
        REDIRECT: 'http://localhost:8090/socials/oauth/callback'
    });