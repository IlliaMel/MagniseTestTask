package com.infinity.apps.magnisetesttask.data.remote.interceptor

import com.infinity.apps.magnisetesttask.domain.local.repository.ITokenCacheRepository
import okhttp3.Interceptor
import retrofit2.Invocation
import okhttp3.Response
import javax.inject.Inject

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class Authenticated

class AuthInterceptor @Inject constructor(private val tokenCacheRepository: ITokenCacheRepository) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        val invocation = request.tag(Invocation::class.java) ?: return chain.proceed(request)
        val shouldAttachAuthHeader = invocation.method().annotations.any { it is Authenticated }

        return if (shouldAttachAuthHeader) {
            val token = /*tokenCacheRepository.getAccessToken()*/"eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJTUDJFWmlsdm8zS2g3aGEtSFRVU0I3bmZ6dERRN21tb3M3TXZndlI5UnZjIn0.eyJleHAiOjE3MzAwNjI2NjAsImlhdCI6MTczMDA2MDg2MCwianRpIjoiMDRiOTM4MTctMDUyOC00YWY3LWExM2YtYmNkMDg1OGIyNmZhIiwiaXNzIjoiaHR0cHM6Ly9wbGF0Zm9ybS5maW50YWNoYXJ0cy5jb20vaWRlbnRpdHkvcmVhbG1zL2ZpbnRhdGVjaCIsImF1ZCI6WyJuZXdzLWNvbnNvbGlkYXRvciIsImJhcnMtY29uc29saWRhdG9yIiwidHJhZGluZy1jb25zb2xpZGF0b3IiLCJlZHVjYXRpb24iLCJjb3B5LXRyYWRlci1jb25zb2xpZGF0b3IiLCJwYXltZW50cyIsIndlYi1zb2NrZXRzLXN0cmVhbWluZyIsInVzZXItZGF0YS1zdG9yZSIsImFsZXJ0cy1jb25zb2xpZGF0b3IiLCJ1c2VyLXByb2ZpbGUiLCJlbWFpbC1ub3RpZmljYXRpb25zIiwiaW5zdHJ1bWVudHMtY29uc29saWRhdG9yIiwiYWNjb3VudCJdLCJzdWIiOiI5NWU2NmRiYi00N2E3LTQ4ZDktOWRmZS00ZWM2Y2U0MWNiNDEiLCJ0eXAiOiJCZWFyZXIiLCJhenAiOiJhcHAtY2xpIiwic2Vzc2lvbl9zdGF0ZSI6ImVkZjIyNmE2LTJiYWUtNDRkZC1hODkwLTQxNTAyZDM1NTZmYyIsImFjciI6IjEiLCJyZWFsbV9hY2Nlc3MiOnsicm9sZXMiOlsib2ZmbGluZV9hY2Nlc3MiLCJkZWZhdWx0LXJvbGVzLWZpbnRhdGVjaCIsInVtYV9hdXRob3JpemF0aW9uIiwidXNlcnMiXX0sInJlc291cmNlX2FjY2VzcyI6eyJhY2NvdW50Ijp7InJvbGVzIjpbIm1hbmFnZS1hY2NvdW50IiwibWFuYWdlLWFjY291bnQtbGlua3MiLCJ2aWV3LXByb2ZpbGUiXX19LCJzY29wZSI6ImVtYWlsIHByb2ZpbGUiLCJzaWQiOiJlZGYyMjZhNi0yYmFlLTQ0ZGQtYTg5MC00MTUwMmQzNTU2ZmMiLCJlbWFpbF92ZXJpZmllZCI6dHJ1ZSwicm9sZXMiOlsib2ZmbGluZV9hY2Nlc3MiLCJkZWZhdWx0LXJvbGVzLWZpbnRhdGVjaCIsInVtYV9hdXRob3JpemF0aW9uIiwidXNlcnMiXSwibmFtZSI6IkRlbW8gVXNlciIsInByZWZlcnJlZF91c2VybmFtZSI6InJfdGVzdEBmaW50YXRlY2guY29tIiwiZ2l2ZW5fbmFtZSI6IkRlbW8iLCJmYW1pbHlfbmFtZSI6IlVzZXIiLCJlbWFpbCI6InJfdGVzdEBmaW50YXRlY2guY29tIn0.MkEDCmtUW2F_h6qkVTMrogOIg6KfqMWRywP3bjxZlKWOikgIupLj13n5jbZJt0qJrWXno6Uy_VJaObY1jJNsQnQPh4MWnFbvymoUR5ES-MmPR3G1-xW5z6i5XhHb5vDkMXhA24-Rax-hM-pG4cswuBCC1lnw6ZvV85PCvzaIIiapR3NmaTkz1SIghCh1UnGIQ7eozBKia8WMrfO6501N6J5qIcClqQMMqg0biCMg2EHweDfkE6W-Ebdo_mVW_4ep777-KuBI4Co5f_lYoM69QaUKw0cWl7N1x8QZbxmDsXJ_ZRmnIRVupl3TZtyxVJ_hGC2NjXeS4tRZAUD5FnN6kQ"
            if (token != null && token.isNotEmpty()) {
                val authenticatedRequest = request.newBuilder()
                    .addHeader("Authorization", "Bearer $token")
                    .build()
                chain.proceed(authenticatedRequest)
            } else {
                chain.proceed(request)
            }
        } else {
            chain.proceed(request)
        }
    }
}