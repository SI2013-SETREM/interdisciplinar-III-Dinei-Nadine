if (typeof($.fn.dataTable) !== 'undefined') {
    $.extend($.fn.dataTable.defaults, {
        "language": {
            "emptyTable": "Nenhum registro encontrado",
            "info": "Mostrando _START_ - _END_ de _TOTAL_ registros",
            "infoEmpty": "Mostrando 0 - 0 de 0 registros",
            "infoFiltered": "(Filtrados de _MAX_ registros)",
            "infoPostFix": "",
            "infoThousands": ".",
            "lengthMenu": "_MENU_ registros por página",
            "loadingRecords": "Carregando...",
            "processing": "Processando...",
            "zeroRecords": "Nenhum registro encontrado",
            "search": "<i class='fa fa-search'></i>",
            "paginate": {
                "next": "Próxima",
                "previous": "Anterior",
                "first": "",
                "last": ""
            },
            "aria": {
                "sortAscending": ": Ordenar colunas de forma ascendente",
                "sortDescending": ": Ordenar colunas de forma descendente"
            }
        },
        "pageLength": 50,
        "lengthMenu": [ 5, 10, 50, 100, 200 ],
        "renderer": "bootstrap",
        "pagingType": "full_numbers",
        "columnDefs": [ {
            "targets": [-1, -2],
            "orderable": false,
            "searchable": false
        } ],
        "dom": '<"dataTables_wrapper form-inline dt-bootstrap no-footer"<"row"<"col-sm-6"i><"col-sm-6"f>><"row"<"col-sm-12"rt>><"row"<"col-sm-5"l><"col-sm-7"p>>>'
    });
}

var WsRequestOperacao = {
    LIST: 'LIST',
    INSERT: 'INSERT',
    UPDATE: 'UPDATE',
    DELETE: 'DELETE',
};

function addItemData($before, placeholder, nameId, type) {
    if (!type) {
        type = 'text';
    }
    var n = $before.parent().children('.item-data').length;
    if (n) {
        var $lastItemData = $($before.parent().children('.item-data').get(n-1));
        if ($lastItemData.length) {
            n = $lastItemData.data('n')+1;
        }
    }
    var $focusOn;
    $('<div/>', {
        'class': 'list-group-item item-data',
        html: $('<div/>', {
            'class': 'row',
            html: [
                $('<div/>', {
                    'class': 'col-md-11 col-sm-11 col-xs-11',
                    html: $focusOn = $('<input/>', {
                        'class': 'form-control',
                        'type': type,
                        'placeholder': placeholder,
                        'name': nameId + '[' + n + ']',
                        'id': nameId + n,
                    }),
                }),
                $('<div/>', {
                    'class': 'col-md-1 col-sm-1 col-xs-1',
                    html: $('<a/>', {
                        'href': 'javascript:void(0);',
                        'class': 'fa fa-form-control fa-trash delTelefoneFornecedor',
                    })
                }),
            ],
        }),
    }).data('n', n).insertBefore($before);
    $focusOn.focus();
}

function delItemData($btnDel) {
    if ($btnDel.length) {
        $btnDel.parents('.item-data').remove();
    }
}

$(document).ready(function () {

    $('.alert-dismissable .close').click(function() {
        $(this).parent().fadeOut(200);
        return false;
    });
    
    $('#addEmailFornecedor').click(function() {
        addItemData($(this), 'Email do fornecedor', 'emails', 'email');
    });
    $('#addTelefoneFornecedor').click(function() {
        addItemData($(this), 'Telefone do fornecedor', 'telefones', 'tel');
    });
    $('#addComponenteProduto').click(function() {
        var $this = $(this),
            nameId = 'produtoComponentes';
        var n = $this.parent().children('.item-data').length;
        if (n) {
            var $lastItemData = $($this.parent().children('.item-data').get(n-1));
            if ($lastItemData.length) {
                n = $lastItemData.data('n')+1;
            }
        }
        
        var optionsComponente = [
            $('<option/>', {
                'value': '',
            }),
        ];
        if (componentes && componentes.length) {
            componentes.forEach(function (comp) {
                optionsComponente[optionsComponente.length] = $('<option/>', {
                    'value': comp.id,
                    text: comp.nome,
                });
            });
        }
        
        var $focusOn;
        $('<div/>', {
            'class': 'list-group-item item-data',
            html: [
                $('<div/>', {
                    'class': 'form-group',
                    html: [
                        $('<label/>', {
                            'class': 'control-label col-md-2 col-sm-5',
                            'for': nameId + n + '.componenteId',
                            text: 'Componente:',
                        }),
                        $('<div/>', {
                            'class': 'col-md-4 col-sm-7',
                            html: $focusOn = $('<select/>', {
                                'class': 'form-control',
                                'required': true,
                                'name': nameId + '[' + n + '].componenteId',
                                'id': nameId + n + '.componenteId',
                                html: optionsComponente
                            }),
                        }),
                        $('<label/>', {
                            'class': 'control-label col-md-2 col-sm-5',
                            'for': nameId + n + '.quantidade',
                            text: 'Quantidade:',
                        }),
                        $('<div/>', {
                            'class': 'col-md-3 col-sm-7',
                            html: $('<input/>', {
                                'class': 'form-control',
                                'type': 'text',
                                'placeholder': 'Quantidade utilizada',
                                'name': nameId + '[' + n + '].quantidade',
                                'id': nameId + n + '.quantidade',
                            }),
                        }),
                        $('<div/>', {
                            'class': 'col-md-1 col-sm-1 col-xs-1',
                            html: $('<a/>', {
                                'href': 'javascript:void(0);',
                                'class': 'fa fa-form-control fa-trash delComponenteProduto',
                            })
                        }),
                    ],
                }),
                $('<div/>', {
                    'class': 'form-group',
                    html: [
                        $('<label/>', {
                            'class': 'control-label col-md-2 col-sm-5',
                            'for': nameId + n + '.comentario',
                            text: 'Anotação:',
                        }),
                        $('<div/>', {
                            'class': 'col-md-9 col-sm-7',
                            html: $('<textarea/>', {
                                'class': 'form-control',
                                'rows': '2',
                                'placeholder': 'Anotação extra',
                                'name': nameId + '[' + n + '].comentario',
                                'id': nameId + n + '.comentario',
                            }),
                        }),
                    ],
                }),
            ],
        }).data('n', n).insertBefore($this);
        $focusOn.focus();
    });
    // Para aplicar a elementos adicionados dinamicamente também
    $(document).on('click', '.delTelefoneFornecedor, .delEmailFornecedor', function () {
        delItemData($(this));
    });
    $(document).on('click', '.delComponenteProduto', function () {
        if (confirm('Deseja realmente remover este componente?')) {
            delItemData($(this));
        }
    });
    
    // Fallback para navegadores que não suportam HTML5
    //Documentação: http://xdsoft.net/jqplugins/datetimepicker/
    $('input[type=datetime]').get().forEach(function (input, idx) {
        if (input.type === 'text') { //o navegador não suporta HTML5
            $(input).datetimepicker({
                'lang': 'pt-BR',
                'format': 'Y-m-d H:i',
                'step': 15,
            });
        }
    });
    $('input[type=date]').get().forEach(function (input, idx) {
        if (input.type === 'text') { //o navegador não suporta HTML5
            $(input).datetimepicker({
                'timepicker': false,
                'lang': 'pt-BR',
                'format': 'Y-m-d',
                'step': 15,
            });
        }
    });
    
    //@TODO
//    $('input.money').maskMoney();

    //METIS MENU 
    if (typeof($.fn.metisMenu) !== 'undefined') {
        $('#main-menu').metisMenu();
    }

    //LOAD APPROPRIATE MENU BAR
    $(window).bind("load resize", function () {
        if ($(this).width() < 768) {
            $('div.sidebar-collapse').addClass('collapse')
        } else {
            $('div.sidebar-collapse').removeClass('collapse')
        }
    });

    if (typeof($.fn.dataTable) !== 'undefined') {
        if (typeof(dataTableOpts) === 'undefined') {
            dataTableOpts = {};
        }
        $('.dataTable').dataTable(dataTableOpts);
    }

});


/*

//METIS MENU 
$('#main-menu').metisMenu();

//LOAD APPROPRIATE MENU BAR
$(window).bind("load resize", function () {
    if ($(this).width() < 768) {
        $('div.sidebar-collapse').addClass('collapse')
    } else {
        $('div.sidebar-collapse').removeClass('collapse')
    }
});


$('.dataTable').dataTable();


//CHARTS
//BAR CHART
Morris.Bar({
    element: 'morris-bar-chart',
    data: [{
        y: '2006',
        a: 100,
        b: 90
    }, {
        y: '2007',
        a: 75,
        b: 65
    }, {
        y: '2008',
        a: 50,
        b: 40
    }, {
        y: '2009',
        a: 75,
        b: 65
    }, {
        y: '2010',
        a: 50,
        b: 40
    }, {
        y: '2011',
        a: 75,
        b: 65
    }, {
        y: '2012',
        a: 100,
        b: 90
    }],
    xkey: 'y',
    ykeys: ['a', 'b'],
    labels: ['Series A', 'Series B'],
    hideHover: 'auto',
    resize: true
});

//DONUT CHART
Morris.Donut({
    element: 'morris-donut-chart',
    data: [{
        label: "Download Sales",
        value: 12
    }, {
        label: "In-Store Sales",
        value: 30
    }, {
        label: "Mail-Order Sales",
        value: 20
    }],
    resize: true
});

//AREA CHART
Morris.Area({
    element: 'morris-area-chart',
    data: [{
        period: '2010 Q1',
        iphone: 2666,
        ipad: null,
        itouch: 2647
    }, {
        period: '2010 Q2',
        iphone: 2778,
        ipad: 2294,
        itouch: 2441
    }, {
        period: '2010 Q3',
        iphone: 4912,
        ipad: 1969,
        itouch: 2501
    }, {
        period: '2010 Q4',
        iphone: 3767,
        ipad: 3597,
        itouch: 5689
    }, {
        period: '2011 Q1',
        iphone: 6810,
        ipad: 1914,
        itouch: 2293
    }, {
        period: '2011 Q2',
        iphone: 5670,
        ipad: 4293,
        itouch: 1881
    }, {
        period: '2011 Q3',
        iphone: 4820,
        ipad: 3795,
        itouch: 1588
    }, {
        period: '2011 Q4',
        iphone: 15073,
        ipad: 5967,
        itouch: 5175
    }, {
        period: '2012 Q1',
        iphone: 10687,
        ipad: 4460,
        itouch: 2028
    }, {
        period: '2012 Q2',
        iphone: 8432,
        ipad: 5713,
        itouch: 1791
    }],
    xkey: 'period',
    ykeys: ['iphone', 'ipad', 'itouch'],
    labels: ['iPhone', 'iPad', 'iPod Touch'],
    pointSize: 2,
    hideHover: 'auto',
    resize: true
});

//LINE CHART
Morris.Line({
    element: 'morris-line-chart',
    data: [{
        y: '2006',
        a: 100,
        b: 90
    }, {
        y: '2007',
        a: 75,
        b: 65
    }, {
        y: '2008',
        a: 50,
        b: 40
    }, {
        y: '2009',
        a: 75,
        b: 65
    }, {
        y: '2010',
        a: 50,
        b: 40
    }, {
        y: '2011',
        a: 75,
        b: 65
    }, {
        y: '2012',
        a: 100,
        b: 90
    }],
    xkey: 'y',
    ykeys: ['a', 'b'],
    labels: ['Series A', 'Series B'],
    hideHover: 'auto',
    resize: true
});


}(jQuery));
*/
