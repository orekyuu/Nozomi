package org.orekyuu.nozomi.domain.project;

import org.orekyuu.nozomi.domain.basic.event.EventType;

public record ProjectEvent(EventType event, ProjectId id) {
}
