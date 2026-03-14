# Run Experiment

Skill for designing and running AutoML experiments with auto-sklearn.

## When to Use
When the user wants to run an ML experiment, train models, or test auto-sklearn on a dataset.

## Instructions

1. **Dataset Preparation**: Help the user load and prepare their dataset.
   - Support common formats: CSV, ARFF, NumPy arrays, pandas DataFrames
   - Check for missing values, data types, and feature characteristics
   - Split data into train/test sets with appropriate stratification

2. **Experiment Configuration**: Configure auto-sklearn with appropriate settings.
   - Set `time_left_for_this_task` and `per_run_time_limit` based on dataset size
   - Configure `memory_limit` appropriately
   - Select appropriate metric from `autosklearn.metrics`
   - Consider enabling/disabling ensemble building based on use case
   - Set `tmp_folder` and `output_folder` for reproducibility

3. **Run the Experiment**: Execute using the auto-sklearn API.
   ```python
   import autosklearn.classification  # or autosklearn.regression
   import sklearn.model_selection
   import sklearn.metrics

   automl = autosklearn.classification.AutoSklearnClassifier(
       time_left_for_this_task=3600,
       per_run_time_limit=300,
       tmp_folder='/tmp/autosklearn_experiment',
   )
   automl.fit(X_train, y_train)
   predictions = automl.predict(X_test)
   ```

4. **Results Collection**: Gather and organize results.
   - Print `automl.show_models()` for the ensemble composition
   - Print `automl.sprint_statistics()` for summary statistics
   - Log performance metrics (accuracy, F1, AUC, etc.)
   - Save the trained model for reproducibility

5. **Reproducibility**: Ensure experiments are reproducible.
   - Record random seed, time limits, and all configuration parameters
   - Document the auto-sklearn version and dependency versions
   - Save the experiment configuration as a YAML or JSON file
