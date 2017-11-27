/*
 * Copyright (c) 2005, 2014, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.  Oracle designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Oracle in the LICENSE file that accompanied this code.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact Oracle, 500 Oracle Parkway, Redwood Shores, CA 94065 USA
 * or visit www.oracle.com if you need additional information or have any
 * questions.
 */

package com.tora;

import com.ubb.ppp.beans.Order;
import com.ubb.ppp.repository.*;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.OutputTimeUnit;

import java.util.concurrent.TimeUnit;

public class MyBenchmark {
    @Benchmark @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public boolean test_ArrayListBasedRepository_int() {
        return testRepository(new ArrayListBasedRepository<Integer>(), 1);
    }

    @Benchmark @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public boolean test_ArrayListBasedRepository_double() {
        return testRepository(new ArrayListBasedRepository<Double>(), 1D);
    }

    @Benchmark @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public boolean test_ArrayListBasedRepository_object() {
        return testRepository(new ArrayListBasedRepository<Order>(), new Order(1, 10, 100));
    }

    @Benchmark @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public boolean test_ConcurentHashMapBasedRepository_int() {
        return testRepository(new ConcurentHashMapBasedRepository<Integer>(), 1);
    }

    @Benchmark @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public boolean test_ConcurentHashMapBasedRepository_double() {
        return testRepository(new ConcurentHashMapBasedRepository<Double>(), 1D);
    }

    @Benchmark @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public boolean test_ConcurentHashMapBasedRepository_object() {
        return testRepository(new ConcurentHashMapBasedRepository<Order>(), new Order(1, 10, 100));
    }

    @Benchmark @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public boolean test_EclipseCollectionsBasedRepository_int() {
        return testRepository(new EclipseCollectionsBasedRepository<Integer>(), 1);
    }

    @Benchmark @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public boolean test_EclipseCollectionsBasedRepository_double() {
        return testRepository(new ConcurentHashMapBasedRepository<Double>(), 1D);
    }

    @Benchmark @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public boolean test_EclipseCollectionsBasedRepository_object() {
        return testRepository(new ConcurentHashMapBasedRepository<Order>(), new Order(1, 10, 100));
    }

    @Benchmark @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public boolean test_HashSetBasedRepository_int() {
        return testRepository(new HashSetBasedRepository<Integer>(), 1);
    }

    @Benchmark @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public boolean test_HashSetBasedRepository_double() {
        return testRepository(new HashSetBasedRepository<Double>(), 1D);
    }

    @Benchmark @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public boolean test_HashSetBasedRepository_object() {
        return testRepository(new HashSetBasedRepository<Order>(), new Order(1, 10, 100));
    }

    @Benchmark @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public boolean test_TreeSetBasedRepository_int() {
        return testRepository(new TreeSetBasedRepository<Integer>(), 1);
    }

    @Benchmark @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public boolean test_TreeSetBasedRepository_double() {
        return testRepository(new HashSetBasedRepository<Double>(), 1D);
    }

    @Benchmark @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public boolean test_TreeSetBasedRepository_object() {
        return testRepository(new HashSetBasedRepository<Order>(), new Order(1, 10, 100));
    }

    @Benchmark @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public boolean test_IntEclipseBasedRepository_int() {
        return testRepository(new IntEclipseBasedRepository(), 1);
    }

    @Benchmark @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public boolean test_DoubleEclipseBaseRepository_double() {
        return testRepository(new DoubleEclipseBaseRepository(), 1D);
    }

    @Benchmark @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public boolean test_IntFastUtilBaseRepository_int() {
        return testRepository(new IntFastUtilBaseRepository(), 1);
    }

    @Benchmark @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public boolean test_DoubleFastUtilBaseRepository_double() {
        return testRepository(new DoubleFastUtilBaseRepository(), 1D);
    }

    public <T> boolean testRepository(InMemoryRepository<T> repo, T elem) {
        repo.add(elem);
        boolean contains =  repo.contains(elem);
        repo.remove(elem);
        contains = contains && repo.contains(elem);
        return contains;
    }
}
