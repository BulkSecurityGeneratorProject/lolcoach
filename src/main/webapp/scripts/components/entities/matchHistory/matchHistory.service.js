'use strict';

angular.module('lolcoachApp')
    .factory('MatchHistory', function ($resource, DateUtils) {
        return $resource('api/matchHistorys/:id', {}, {
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
