package com.bambang.githubsearch

import com.bambang.githubsearch.app.AppComponent
import com.bambang.githubsearch.ext.ViewScope
import com.bambang.githubsearch.view.detail.DetailsFragment
import com.bambang.githubsearch.view.search.SearchFragment
import dagger.Component


@ViewScope
@Component(dependencies = arrayOf(AppComponent::class))
interface MainComponent {

    fun inject(searchFragment: SearchFragment)

    fun inject(detailsFragment: DetailsFragment)

}