package org.apache.maven.surefire.its.jiras;

/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

import org.apache.maven.it.VerificationException;
import org.apache.maven.surefire.its.fixture.SurefireJUnit4IntegrationTestCase;
import org.apache.maven.surefire.its.fixture.SurefireLauncher;
import org.junit.Test;

import java.nio.charset.Charset;
import java.util.List;

import static org.apache.maven.surefire.its.fixture.HelperAssertions.assumeJavaVersion;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.startsWith;

/**
 * IT for https://issues.apache.org/jira/browse/SUREFIRE-1177
 *
 * @author <a href="mailto:tibordigana@apache.org">Tibor Digana (tibor17)</a>
 * @since 2.19
 */
public class Surefire1177TestngParallelSuitesIT
    extends SurefireJUnit4IntegrationTestCase
{
    private static final String EXPECTED_LINE = "TestNGSuiteTest#shouldRunAndPrintItself()";

    @Test
    public void shouldRunTwoSuitesInParallel()
        throws VerificationException
    {
        assumeJavaVersion( 1.7d );

        System.out.println( "our encoding = " + Charset.defaultCharset() );
        System.out.println( asInts( EXPECTED_LINE.toCharArray() ) );

        List<String> lines =
        unpack().executeTest()
            .verifyErrorFree( 2 )
                .loadLogLines();

        System.out.println( "lines - 14 : " + lines.get( lines.size() - 14 ) );
        System.out.println( "lines - 13 : " + lines.get( lines.size() - 13 ) );

        System.out.println( "lines - 14 : " + asInts( lines.get( lines.size() - 14 ).toCharArray() ) );
        System.out.println( "lines - 13 : " + asInts( lines.get( lines.size() - 13 ).toCharArray() ) );

        System.out.println( lines.get( lines.size() - 14 ).contains( EXPECTED_LINE ) );
        System.out.println( lines.get( lines.size() - 13 ).contains( EXPECTED_LINE ) );

        System.out.println( containsString( EXPECTED_LINE ).matches( lines.get( lines.size() - 14 ) ) );
        System.out.println( containsString( EXPECTED_LINE ).matches( lines.get( lines.size() - 13 ) ) );

                //.assertThatLogLine( containsString( "TestNGSuiteTest#shouldRunAndPrintItself()" ), is( 2 ) );
            /*.assertThatLogLine( containsString( "ShouldNotRunTest#shouldNotRun()" ), is( 0 ) )
            .assertThatLogLine( containsString( "TestNGSuiteTest#shouldRunAndPrintItself()" ), is( 2 ) )
            .assertThatLogLine( is( "TestNGSuiteTest#shouldRunAndPrintItself() 1." ), is( 1 ) )
            .assertThatLogLine( is( "TestNGSuiteTest#shouldRunAndPrintItself() 2." ), is( 1 ) );*/
    }

    private SurefireLauncher unpack()
    {
        return unpack( "testng-parallel-suites" );
    }

    private String asInts( char[] args )
    {
        StringBuilder b = new StringBuilder();
        for ( char arg : args )
        {
            b.append( (int) arg )
            .append( ", " );
        }
        return b.toString();
    }
}
