'use strict';

angular.module('lolcoachApp')
    .factory('Summoner', function ($resource, DateUtils) {
        return $resource('api/summoners/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    });
