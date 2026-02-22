package com.chuckerteam.chucker.sample.compose

import com.chuckerteam.chucker.sample.InterceptorType

internal data class ChuckerSampleCallbacks(
    val onInterceptorTypeChange: (InterceptorType) -> Unit,
    val onInterceptorTypeLabelClick: () -> Unit,
    val onDoHttp: () -> Unit,
    val onDoGraphQL: () -> Unit,
    val onLaunchChucker: () -> Unit,
    val onLaunchChuckerInApp: () -> Unit,
    val onExportToLogFile: () -> Unit,
    val onExportToHarFile: () -> Unit,
)
