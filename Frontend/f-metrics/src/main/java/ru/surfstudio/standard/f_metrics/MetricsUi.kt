package ru.surfstudio.standard.f_metrics

import ru.surfstudio.standard.domain.entity.Ipu

data class MetricsUi(
    val ipu: Ipu,
    val previousValue: Int,
    val previousValueString: String
)