(function() {
    'use strict';
    angular
        .module('fleetApp')
        .factory('Device', Device);

    Device.$inject = ['$resource', 'DateUtils'];

    function Device ($resource, DateUtils) {
        var resourceUrl =  'api/devices/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.lastUpdate = DateUtils.convertDateTimeFromServer(data.lastUpdate);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
