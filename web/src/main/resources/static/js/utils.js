const STAND_ESA_TYPE_TEMP = "ESA_TEMP";
const STAND_ESA_TYPE_REGULAR = "ESA_REG";

const STAND_TYPE = Object.freeze({
    'ENV': 1,
    'ESA': 2
});

const STAND_STATE_ON_WORK = "ON_WORK";

const standGroupBackground = ['bg-success2', 'bg-warning', 'bg-transparent'];

class Stand {
    constructor(standId, standState, cardElement, standType) {
        this.id = standId;
        this.state = '';
        this.card = cardElement;
        this.standType = standType
        this.card.data('stand', this);
        this.updateState();
    }

    setStandGroupBackground(cssClassName) {
        let stand = this
        if (stand.card.hasClass(cssClassName)) {
            return
        }
        standGroupBackground.forEach(bg => stand.card.removeClass(bg))
        stand.card.addClass(cssClassName)
    }

    updateState(force = false) {
        let url = "/api/stand/" + this.id + "/standState"
        let stand = this
        let oldState = stand.state
        $.getJSON(url, function (data) {
            stand.state = data
            if (stand.state === oldState && !force) {
                return;
            }
            stand.onUpdateState(oldState, stand.state);

            if (stand.state === STAND_STATE_ON_WORK) {
                stand.setStandGroupBackground('bg-success2')
            } else {
                stand.setStandGroupBackground('bg-transparent')
            }
        })
    }

    onUpdateState(oldState, newState) {
        this.updateGroupChecks();
    }

    updateGroupChecks() {
    }
}

class EsaStand extends Stand {
    constructor(standId, standState, cardElement) {
        super(standId, standState, cardElement, STAND_TYPE.ESA);
        this.tableChecks = cardElement.find('.stand-checks-table-body');
        this.clock = cardElement.find('.stand-update-date');
        this.versionElement = cardElement.find('.stand-version');
        this.builDateElement = cardElement.find('.stand-build-date');
        this.menuElement = cardElement.find('.stand-menu');

        const stand = this;

        this.menuElement.find('.stand-check').click(function () {
            stand.checkStand();
        });

        this.menuElement.find('.stand-halt').click(function () {
            stand.checkOff();
        })
    };

    buildGroupRow(group, statGroup) {
        return `
<tr>
   <td class="check-group-name"><span data-toggle="tooltip" data-placement="top" title="${group.description}">${group.caption}</span></td>
   <td class="check-group-value"><a>${statGroup.total()} </a>
   </td>
</tr>`;
    };

    checkStand() {
        let oldCaption = this.menuElement.find('button').text()
        this.menuElement.find('button').text("...")
        const url = "/api/stand/" + this.id + "/check";
        const stand = this;
        $.ajax({
            url: url,
            type: 'GET'
        }).done(function (data) {
            if ((data.toString() === "true" && stand.state !== STAND_STATE_ON_WORK) || (data.toString() === "false" && stand.state === STAND_STATE_ON_WORK)) {
                stand.updateState(true);
            } else {
                stand.menuElement.find('button').text(oldCaption);
            }
        }).fail(function (msg) {
            stand.menuElement.find('button').text(oldCaption);
            stand.checkOff();
        });
    };

    checkOff() {
        let oldCaption = this.menuElement.find('button').text()
        this.menuElement.find('button').text("...")
        const url = "/api/stand/" + this.id + "/checkOff";
        const stand = this;
        $.ajax({
            url: url,
            type: 'PUT'
        }).done(function (data) {
            if (data.toString() === "true" && stand.state === STAND_STATE_ON_WORK) {
                stand.updateState(true);
            } else {
                stand.menuElement.find('button').text(oldCaption);
            }
        }).fail(function () {
            stand.menuElement.find('button').text(oldCaption);
        })
    }

    updateGroupChecks() {
        const stand = this
        if (stand.state !== STAND_STATE_ON_WORK) {
            stand.tableChecks.empty();
            return;
        }
        const url = "/api/stand/" + this.id + "/checkGroups/lastResults";
        $.getJSON(url, function (data) {
            if (stand.state !== STAND_STATE_ON_WORK) {
                stand.tableChecks.empty();
                return;
            }
            const groups = data;
            stand.tableChecks.empty();
            let lastDate = "00:00";
            let existsError = false;
            for (const key in groups) {
                const group = groups[key]
                const statGroup = getStat(group.lastResults)
                if (statGroup.cnt > 0) {
                    stand.tableChecks.append(stand.buildGroupRow(group, statGroup, stand.id))

                    existsError = statGroup.errorExists() || existsError;
                }
                lastDate = maxStr(lastDate, statGroup.lastDateCheck);
            }


            if (existsError) {
                stand.setStandGroupBackground('bg-warning')
            } else {
                stand.setStandGroupBackground('bg-success2')
            }


            stand.clock.text("Last updated: " + lastDate.substr(11));
        })
    }


    onUpdateState(oldState, newState) {
        super.onUpdateState(oldState, newState);
        this.updateCard();
        // if (newState === STAND_STATE_ON_WORK) {
        //
        // } else {
        //     this.versionElement.text("");
        //     this.builDateElement.text("");
        // }
    }

    updateCard() {
        let url = "/api/stand/" + this.id + "/standEsaInfo";
        const stand = this;
        $.getJSON(url, function (data) {
            const esaInfo = data;
            stand.versionElement.text(esaInfo.version);
            stand.builDateElement.text(esaInfo.buildDate);
            stand.menuElement.find('button').text(esaInfo.stateCaption);
        });
    }
}

class EnvStand extends Stand {
    constructor(standId, standState, cardElement) {
        super(standId, standState, cardElement, STAND_TYPE.ENV);
    }

    updateGroupChecks() {
        let stand = this
        $.getJSON("/api/stand/" + stand.id + "/checkGroups/lastResults", function (data) {
            const groups = data

            for (const key in groups) {
                const group = groups[key]
                const statGroup = getStat(group.lastResults)

                if (statGroup.errorExists()) {
                    stand.setStandGroupBackground('bg-warning')
                }
            }
        });
    }
}

const standManagement = {
    esaStands: new Map(),

    addEsaStand: function (standId, standState, cardElement) {
        let stand = new EsaStand(standId, standState, cardElement);
        this.esaStands.set(standId, stand);
        stand.updateGroupChecks();
        return stand;
    },

    addEnvStand: function (standId, standState, cardElement) {
        let stand = new EnvStand(standId, standState, cardElement);
        this.esaStands.set(standId, stand);
    },

    checkStandsState: function () {
        for (let stand of this.esaStands.values()) {
            stand.updateState();
            if (stand.state === STAND_STATE_ON_WORK) {
                stand.updateGroupChecks();
            }
        }
    }
};

function sleep(ms) {
    const date = Date.now();
    let currentDate = null;
    do {
        currentDate = Date.now();
    } while (currentDate - date < ms);
}

function dateFormat(date) {
    let newDate = new Date(Date.parse(date)).toLocaleString();
    return newDate == 'Invalid Date' ? date : newDate;
}

function getJsonData(url) {
    let data = {};

    $.ajax({
        url: url,
        dataType: 'json',
        data: data,
        async: false,
        success: function (response) {
            data = response
        }
    });
    return data;
}

function getTextData(url) {
    let data = "";

    $.ajax({
        url: url,
        dataType: 'text',
        data: data,
        async: false,
        success: function (response) {
            data = response
        }
    });
    return data;
}

function changeClassElement(element, oldClassName, newClassName) {
    if (element.classList.contains(oldClassName)) {
        element.classList.remove(newClassName);
    }
    element.classList.add(newClassName);
}

function maxStr(...args) {
    let res = "";
    for (let arg of args) {
        let strArg = arg.toString();
        if (strArg.length > 0 && strArg > res) {
            res = strArg;
        }
    }
    return res;
}

function getStat(lastResults) {
    let statGroup = {
        cnt: 0,
        success: 0,
        data: [],
        total: function () {
            return this.success + "/" + this.cnt;
        },
        errorExists: function () {
            return this.success !== this.cnt;
        },
        tableRows: "",
        lastDateCheck: ""
    };

    for (const key in lastResults) {
        const lastResult = lastResults[key]
        statGroup.cnt++;
        if (lastResult.isSuccess) {
            statGroup.success++;
        }
        statGroup.lastDateCheck = maxStr(statGroup.lastDateCheck, lastResult.dateStart)
        statGroup.data.push(lastResult)
    }
    return statGroup;
}
